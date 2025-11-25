package ai;

import entity.Entity;
import main.GamePanel;
import main.Main;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp){
        this.gp = gp;
        instantiateNodes();
    }

    public void instantiateNodes(){
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow){
            node[col][row] = new Node(col, row);

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void resetNode(){
        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow){
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity){
        resetNode();

        //Set Start and Goal Node
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            //Set Solid Node
            //Check Tiles
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
            if(gp.tileM.tile[tileNum].collision == true){
                node[col][row].solid = true;
            }

            //Check Interactive Tile
            for(int i = 0; i < gp.iTile[1].length; i++){
                if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible == true){
                    int itCol = gp.iTile[gp.currentMap][i].worldX/gp.tileSize;
                    int itRow = gp.iTile[gp.currentMap][i].worldY/gp.tileSize;
                    node[itCol][itRow].solid = true;
                }
            }

            //Set Cost
            getCost(node[col][row]);

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }

        if(startNode.solid) startNode.solid = false;
        if(goalNode.solid) goalNode.solid = false;
    }

    public void getCost(Node node){
        // G Cost = distance from start (Manhattan)
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H Cost = distance to goal (Manhattan)
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F Cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while (goalReached == false && step < 500){

            int col = currentNode.col;
            int row = currentNode.row;

            //Check The Current Node
            currentNode.checked = true;
            openList.remove(currentNode);

            // UP
            if (row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }
            // LEFT
            if (col - 1 >= 0) {               // ✅ FIXED
                openNode(node[col - 1][row]);
            }

            // DOWN
            if (row + 1 < gp.maxWorldRow) {
                openNode(node[col][row + 1]);
            }

            // RIGHT
            if (col + 1 < gp.maxWorldCol) {   // ✅ FIXED
                openNode(node[col + 1][row]);
            }

            //Find The Best Node
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for(int i = 0; i < openList.size(); i++){
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                else if (openList.get(i).fCost == bestNodefCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }

            //If There is no Node in the Open List, end the Loop
            if(openList.size() == 0){
                break;
            }

            //After the Loop, openList[bestNodeIndex] is the next step (= currentNode)
            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }

    public void openNode(Node node){
        if(node.open == false && node.checked == false && node.solid == false){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);

        }
    }

    public void trackThePath(){
        Node current = goalNode;

        while (current != startNode){
            pathList.add(0, current);
            current = current.parent;
        }
    }

}
