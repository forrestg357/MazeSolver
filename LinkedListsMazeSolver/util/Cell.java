package util;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Maze;

public class Cell {

	private int x, y, id, distance, weight;
	
	private Cell parent;
	
	private boolean visited = false;
	private boolean path = false;
	private boolean deadEnd = false;
	
	private boolean[] walls = {true, true, true, true};
	
	public boolean[] getWalls() {
		return walls;
	}

	public void setWalls(boolean[] walls) {
		this.walls = walls;
	}

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		
		//creates random weights for each cell
		this.weight = (int) (Math.random() * 100);
		
		//Evaluates the distance from cell to the end for a 25 by 25 maze
		this.distance = (int) (Math.sqrt((24 - x) * (24 - x) + (24 - y) * (24 - y)));

		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public boolean isDeadEnd() {
		return deadEnd;
	}
	
	public void setDeadEnd(boolean deadEnd) {
		this.deadEnd = deadEnd;
	}
	
	//Checks if the cell is a dead end based on 
	//if valid neighbors have already been visited
	public boolean checkDeadEnd(List<Cell> grid) { 
		
		for (Cell cell : this.getValidMoveNeighbours(grid)) {
			if (!cell.isVisited()) {
				return false;
			}
		}
		
		return true;
	}

	public boolean isPath() {
		return path;
	}

	public void setPath(boolean path) {
		this.path = path;
	}
	
	public int getDistance() { //Retrieves the distance from cell to the end
		return distance;
	}
	
	public void setDistance(int distance) { //Sets the distance from cell to the end
		this.distance = distance;
	}
	 
	public int getWeight() { //Retrieves the weight
		return weight;
	}

	public void setWeight(int weight) { //Sets the weight
		this.weight = weight;
	}
	
	public int getTotalWeight() { //Recursively finds total weight (Dijkstra's)
		
		//Total weight is sum of all the weights in the path to a cell as defined by parent
		if (this.getParent() == null) {
			return this.weight;
		}
		return this.weight + this.getParent().getTotalWeight();
	}

	public Cell getParent() {
		return parent;
	}
	
	public void setParent(Cell parent) {
		this.parent = parent;
	}
	
	public void draw(Graphics g) {
		int x2 = x * Maze.W;
	    int y2 = y * Maze.W;
	    
	    if (visited) {
	    	g.setColor(Color.MAGENTA);
	    	g.fillRect(x2, y2, Maze.W, Maze.W);
	    }
	   
	    if (path) {
	    	g.setColor(Color.BLUE);
	    	g.fillRect(x2, y2, Maze.W, Maze.W);
	    } else if (deadEnd) {
	    	g.setColor(Color.RED);
	    	g.fillRect(x2, y2, Maze.W, Maze.W);
	    }
	    
	    g.setColor(Color.WHITE);
	    if (walls[0]) {
	    	g.drawLine(x2, y2, x2+Maze.W, y2);
	    }
	    if (walls[1]) {
	    	g.drawLine(x2+Maze.W, y2, x2+Maze.W, y2+Maze.W);
	    }
	    if (walls[2]) {
	    	g.drawLine(x2+Maze.W, y2+Maze.W, x2, y2+Maze.W);
	    }
	    if (walls[3]) {
	    	g.drawLine(x2, y2+Maze.W, x2, y2);
	    } 
	}
	
	public void displayAsColor(Graphics g, Color color) {
		int x2 = x * Maze.W;
	    int y2 = y * Maze.W;
		g.setColor(color);
    	g.fillRect(x2, y2, Maze.W, Maze.W);
	}
	
	public void removeWalls(Cell next) {
		int x = this.x - next.x;
		 // top 0, right 1, bottom 2, left 3
		
		if(x == 1) {
			walls[3] = false;
			next.walls[1] = false;
		} else if (x == -1) {
			walls[1] = false;
			next.walls[3] = false;
		}
		
		int y = this.y - next.y;
		
		if(y == 1) {
			walls[0] = false;
			next.walls[2] = false;
		} else if (y == -1) {
			walls[2] = false;
			next.walls[0] = false;
		}
	}

	private Cell randomNeighbour(List<Cell> neighbours) {
		if (neighbours.size() > 0) {
			return neighbours.get(new Random().nextInt(neighbours.size()));
		} else {
			return null;
		}
	}
	
	private Cell checkNeighbourInGridBounds(List<Cell> grid, Cell neighbour) {
		if (grid.contains(neighbour)) {
			return grid.get(grid.indexOf(neighbour));
		} else {
			return null;
		}
	}
	
	public Cell getNonPathNeighbour(List<Cell> grid) {

		List<Cell> neighbours = new ArrayList<Cell>(4);
		
		Cell top = checkNeighbourInGridBounds(grid, new Cell(x, y - 1));
		Cell right = checkNeighbourInGridBounds(grid, new Cell(x + 1, y));
		Cell bottom = checkNeighbourInGridBounds(grid, new Cell(x, y + 1));
		Cell left = checkNeighbourInGridBounds(grid, new Cell(x - 1, y));
		
		if (top != null) if(!top.path) neighbours.add(top);
		if (right != null) if(!right.path) neighbours.add(right);
		if (bottom != null) if(!bottom.path) neighbours.add(bottom);
		if (left != null) if(!left.path) neighbours.add(left);
		
		if (neighbours.size() ==  1) {
			return neighbours.get(0);
		}
		return randomNeighbour(neighbours);
	}

	public Cell getUnvisitedNeighbour(List<Cell> grid) {
		
		List<Cell> neighbours = getUnvisitedNeighboursList(grid);
		
		if (neighbours.size() ==  1) {
			return neighbours.get(0);
		}
		return randomNeighbour(neighbours);
	}
	
	public List<Cell> getUnvisitedNeighboursList(List<Cell> grid) {
		
		List<Cell> neighbours = new ArrayList<Cell>(4);
		
		Cell top = checkNeighbourInGridBounds(grid, new Cell(x, y - 1));
		Cell right = checkNeighbourInGridBounds(grid, new Cell(x + 1, y));
		Cell bottom = checkNeighbourInGridBounds(grid, new Cell(x, y + 1));
		Cell left = checkNeighbourInGridBounds(grid, new Cell(x - 1, y));
		
		if (top != null) if(!top.visited) neighbours.add(top);
		if (right != null) if(!right.visited) neighbours.add(right);
		if (bottom != null)if(!bottom.visited) neighbours.add(bottom);
		if (left != null) if(!left.visited)neighbours.add(left);
		
		return neighbours;
	}
	
	// no walls between
	public List<Cell> getValidMoveNeighbours(List<Cell> grid) {
		List<Cell> neighbours = new ArrayList<Cell>(4);
		
		Cell top = checkNeighbourInGridBounds(grid, new Cell(x, y - 1));
		Cell right = checkNeighbourInGridBounds(grid, new Cell(x + 1, y));
		Cell bottom = checkNeighbourInGridBounds(grid, new Cell(x, y + 1));
		Cell left = checkNeighbourInGridBounds(grid, new Cell(x - 1, y));
		
		if (top != null) {
			if (!walls[0]) neighbours.add(top);
		}	
		
		if (right != null) {
			if (!walls[1]) neighbours.add(right);
		}
		
		if (bottom != null) {
			if (!walls[2]) neighbours.add(bottom);
		}
		
		if (left != null) {
			if (!walls[3]) neighbours.add(left);
		}
		
		return neighbours;
	}
		
	public Cell getPathNeighbour(List<Cell> grid) {
		List<Cell> neighbours = new ArrayList<Cell>();
		
		Cell top = checkNeighbourInGridBounds(grid, new Cell(x, y - 1));
		Cell right = checkNeighbourInGridBounds(grid, new Cell(x + 1, y));
		Cell bottom = checkNeighbourInGridBounds(grid, new Cell(x, y + 1));
		Cell left = checkNeighbourInGridBounds(grid, new Cell(x - 1, y));
		
		if (top != null) {
			if (!top.deadEnd) {
				if (!walls[0]) neighbours.add(top);
			}
		}
		
		if (right != null) {
			if (!right.deadEnd) {
				if (!walls[1]) neighbours.add(right);
			}
		}
		
		if (bottom != null) {
			if (!bottom.deadEnd) {
				if (!walls[2]) neighbours.add(bottom);
			}
		}
		
		if (left != null) {
			if (!left.deadEnd) {
				if (!walls[3]) neighbours.add(left);
			}
		}
		
		if (neighbours.size() ==  1) {
			return neighbours.get(0);
		}
		
		return randomNeighbour(neighbours);
	}
	
	public List<Cell> getAllNeighbours(List<Cell> grid) {
		List<Cell> neighbours = new ArrayList<Cell>();
		
		Cell top = checkNeighbourInGridBounds(grid, new Cell(x, y - 1));
		Cell right = checkNeighbourInGridBounds(grid, new Cell(x + 1, y));
		Cell bottom = checkNeighbourInGridBounds(grid, new Cell(x, y + 1));
		Cell left = checkNeighbourInGridBounds(grid, new Cell(x - 1, y));
		
		if (top != null) neighbours.add(top);
		if (right != null) neighbours.add(right);
		if (bottom != null) neighbours.add(bottom);
		if (left != null) neighbours.add(left);
		
		return neighbours;
	}
	
	public Cell getTopNeighbour(List<Cell> grid) {
		return checkNeighbourInGridBounds(grid, new Cell(x, y - 1));
	}
	
	public Cell getRightNeighbour(List<Cell> grid) {
		return checkNeighbourInGridBounds(grid, new Cell(x + 1, y));
	}
	
	public Cell getBottomNeighbour(List<Cell> grid) {
		return checkNeighbourInGridBounds(grid, new Cell(x, y + 1));
	}
	
	public Cell getLeftNeighbour(List<Cell> grid) {
		return checkNeighbourInGridBounds(grid, new Cell(x - 1, y));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}