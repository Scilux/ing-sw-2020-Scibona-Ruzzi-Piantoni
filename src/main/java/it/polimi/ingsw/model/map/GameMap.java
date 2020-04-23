package it.polimi.ingsw.model.map;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.utils.ConstantsContainer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameMap {



    private List<Square> gameMap;
    private Map<Worker, Square> workersPosition;           //forse non serve
    private Square [][]linkToCoordinates = new Square[ConstantsContainer.MAXMAPPOSITION][ConstantsContainer.MAXMAPPOSITION]; // mettere come costanti
    private List<Square> modifiedSquare = new ArrayList<>();

    public GameMap() {
        this.gameMap = MapLoader.loadMap();
        for(Square square: gameMap){
            Integer[] coordinates =  square.getCoordinates();
            linkToCoordinates[coordinates[0]][coordinates[1]] = square;
        }
    }

    //
    //Function to obtain the number of tile from coordinates
    //

    public Square getTileFromCoordinates(Integer[] coordinates){
        return linkToCoordinates[coordinates[0]][coordinates[1]];
    }

    //
    //function to find all the reachable square moving from a specific square
    //

    public List<Directions> reachableSquares(Worker worker){
          if(worker == null)
              throw new NullPointerException("null worker");
          int levelPosition = worker.getBoardPosition().getBuildingLevel();
          Map<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();
          List<Directions> reachableSquares = new ArrayList<>();

          for(Directions dir: Directions.values()){
              int squareTile  =canAccess.get(dir);
              if(squareTile > ConstantsContainer.MINMAPPOSITION && squareTile <= ConstantsContainer.MAXMAPPOSITION) {                                              //mettere come costanti
                  Square possibleSquare = gameMap.get(squareTile- 1);
                  if(!possibleSquare.hasPlayer() && (possibleSquare.getBuildingLevel() >= 0 && possibleSquare.getBuildingLevel() <= levelPosition + 1 && !worker.getBoardPosition().equals(possibleSquare) )
                          && possibleSquare.getBuilding() != Building.DOME ){
                      reachableSquares.add(dir);
                  }
              }
          }

return reachableSquares;
    }

    //
    //function that change the position of the worker
    //

    public void moveWorkerTo(Player player, Directions direction){
        if(player == null || direction == null)
            throw new NullPointerException("null player or direction");
        clearModifiedSquare();
        Worker currentWorker = player.getCurrentWorker();
        currentWorker.setPreviousBoardPosition(currentWorker.getBoardPosition());
        modifiedSquare.add(currentWorker.getBoardPosition());
        currentWorker.getPreviousBoardPosition().setHasPlayer(false);
        currentWorker.setBoardPosition( gameMap.get(currentWorker.getBoardPosition().getCanAccess().get(direction) - 1));
        currentWorker.getBoardPosition().setHasPlayer(true);
        currentWorker.getBoardPosition().setPlayer(player);
        currentWorker.getBoardPosition().setWorker(currentWorker);
        modifiedSquare.add(currentWorker.getBoardPosition());
    }

    //
    //function that change the position of the worker
    //

    public List<Directions> buildableSquare(Worker worker){
        if(worker == null)
            throw new NullPointerException("null worker");
        List<Directions> buildableSquare = new ArrayList<>();
        Map<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();

        for(Directions dir: Directions.values()){
              int squareTile = canAccess.get(dir);
              if(squareTile > ConstantsContainer.MINMAPPOSITION && squareTile <= ConstantsContainer.MAXMAPPOSITION){  //mettere come costanti
                  Square possibleBuild = gameMap.get(squareTile - 1);
                  if(!possibleBuild.getBuilding().equals(Building.DOME) && !possibleBuild.hasPlayer() && !worker.getBoardPosition().equals(possibleBuild)){
                      buildableSquare.add(dir);
                  }
              }
        }

        return buildableSquare;
    }

    //
    //function that build in the position selected,with the type of building selected
    //

    public boolean buildInSquare(Worker worker, Directions direction, Building building){
        if(worker == null || direction == null || building == null){
            throw new NullPointerException("null worker or building or direction");
        }
        clearModifiedSquare();
        Square buildingSquare = gameMap.get(worker.getBoardPosition().getCanAccess().get(direction) - 1);
        if(building.equals(Building.mapNext(buildingSquare.getBuilding()))){
            worker.setPreviousBuildPosition(buildingSquare);
            buildingSquare.setBuilding(building);
            buildingSquare.addBuildingLevel();
            clearModifiedSquare();
            modifiedSquare.add(buildingSquare);

            return true;
        }
        return false;

    }

    //
    //function that return the positions of both player's workers
    //

    public List<Square> getWorkersSquares(Player actualPlayer){
        if(actualPlayer == null)
            throw new NullPointerException("player null");
        List<Square> workerSquare = new ArrayList<>();

        for(Worker worker : actualPlayer.getWorkers()){
            workerSquare.add(worker.getBoardPosition());

        }
        return workerSquare;
    }

    public List<Square> getGameMap(){ return gameMap;}


    //
    //function that check if a square is in the perimeter
    //

    public  boolean isInPerimeter(Integer tile){
        if(tile == null)
            throw new NullPointerException("tile null");

        return tile <= ConstantsContainer.PERIMETERPOSITION;
    }


    public void addModifiedSquare(Square square){
        this.modifiedSquare.add(square);
    }

    public List<Square> getModifiedSquare() {
        return modifiedSquare;
    }

    public void clearModifiedSquare(){
        this.modifiedSquare.clear();
    }

    public void removeWorkersOfPlayer(Player player){

        List<Square> workerSquares = getWorkersSquares(player);

        clearModifiedSquare();

        for(Square square: workerSquares){
            addModifiedSquare(square);
            remove(square);
        }
    }

    public void remove(Square square){
        square.setHasPlayer(false);

    }
}
