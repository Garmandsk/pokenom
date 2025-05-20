package ai;

import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {
    GamePanel gameP;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step;

    public PathFinder(GamePanel gameP){
        this.gameP = gameP;
        instantiateNodes();
    }

    public void instantiateNodes(){
        node = new Node[gameP.maxWorldCol][gameP.maxWorldRow];

        int col = 0, row = 0;

        while (col < gameP.maxWorldCol && row < gameP.maxWorldRow){
            node[col][row] = new Node(col, row);

            col++;
            if (col == gameP.maxWorldCol){
                row++;
                col = 0;
            }
        }
    }

    public void resetNodes() {
        int col = 0, row = 0;


        while (col < gameP.maxWorldCol && row < gameP.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if (col == gameP.maxWorldCol) {
                row++;
                col = 0;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCOl, int goalRow){
        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCOl][goalRow];
        openList.add(currentNode);

        int col = 0, row = 0;
        while (col < gameP.maxWorldCol && row < gameP.maxWorldRow){
            int tileNum = gameP.tileM.mapTileNum[gameP.currentMap][col][row];
            if (gameP.tileM.tile[tileNum].collision == true){
                node[col][row].solid = true;
            }

            for (int i = 0; i < gameP.iTile[1].length; i++){
                if (gameP.iTile[gameP.currentMap][i] != null && gameP.iTile[gameP.currentMap][i].destructible == true){
                    int itCol = gameP.iTile[gameP.currentMap][i].worldX/gameP.tileSize;
                    int itRow = gameP.iTile[gameP.currentMap][i].worldY/gameP.tileSize;
                    node[itCol][itRow].solid = true;
                }
            }

            getCost(node[col][row]);

            col++;
            if (col == gameP.maxWorldCol){
                row++;
                col = 0;
            }
        }
    }

    public void getCost(Node node){
        // G Cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance  = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H Cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance  = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while (goalReached == false && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;;

            currentNode.checked = true;
            openList.remove(currentNode);

            if (row-1 >= 0) openNode(node[col][row-1]);
            if (row+1 < gameP.maxWorldRow) openNode(node[col][row+1]);
            if (col-1 >= 0) openNode(node[col-1][row]);
            if (col+1 < gameP.maxWorldCol) openNode(node[col+1][row]);

            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++){
                if (openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodefCost){
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }

            if (openList.size() == 0) break;

            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }

            step++;
        }

        return goalReached;
    }

    public void openNode(Node node){
        if (node.open == false && node.checked == false && node.solid == false){
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
