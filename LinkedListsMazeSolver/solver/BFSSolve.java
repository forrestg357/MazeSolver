package solver;

import java.awt.event.ActionEvent;




import java.awt.event.ActionListener;

import java.util.List;
import javax.swing.Timer;

import main.*;
import util.Cell;
import util.Queue;

public class BFSSolve {
	

	private final List<Cell> grid;
	private Queue<Cell> queue;
	final Timer timer = new Timer(Maze.speed, null);

	public BFSSolve(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		
		for(Cell c : grid) {
			c.setVisited(false);
		}
		
		panel.repaint();

		/*Set up your data structure with the first cell (grid.get(0))*/
		
		queue = new Queue<Cell>();
		queue.add(grid.get(0));
		
		//This starts the timer and runs your maze. You should not
		//need to modify anything else in the constructor
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				solveMaze(panel);
			}
		});
		timer.start();
	}
	
	/**
	 * This is the skeleton for your maze solver. You must implement 
	 * checking if you are done and what to do if you are not.
	 * You will also need to implement the draw path method.
	 */
	private void solveMaze(MazeGridPanel panel) {
		
		if (!queue.peek().equals(grid.get(grid.size() - 1))) { //If the head node does not contain the last cell
			
			Cell currentCell = queue.poll();
			currentCell.setVisited(true); //Only set visited after we have remove
			
			for (Cell cell : currentCell.getValidMoveNeighbours(grid)) {
				
				if (!cell.isVisited()) {
					queue.add(cell); //Adds cells to the queue to be checked
					cell.setParent(currentCell); //Saves the path
				}
			}
			
			if (currentCell.checkDeadEnd(grid)) { //Sets dead end for the color change
				currentCell.setDeadEnd(true); //This is defined in the Cell class
			}
			
		} else {
			drawPath();
			Maze.solved = true;
			timer.stop();
		}
		
		panel.setCurrent(queue.peek());
		panel.repaint();
		timer.setDelay(Maze.speed);
	
	}


	/**
	 * Mark each cell in your solved path with cell.setPath(true)
	 */
	
	private void drawPath() {
		
		//Draws the proper path based on the parents of each cell
		Cell currentCell = queue.peek(); 
		while (currentCell.getParent() != null) {
			
			currentCell.setPath(true);
			currentCell = currentCell.getParent();
			
		}
	}
	
}