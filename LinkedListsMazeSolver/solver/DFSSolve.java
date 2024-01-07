package solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;


import javax.swing.Timer;

import main.*;
import util.Cell;
import util.Stack;

public class DFSSolve {
	

	private final List<Cell> grid;
	private Stack<Cell> stack;
	
	final Timer timer = new Timer(Maze.speed, null);

	public DFSSolve(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		
		for(Cell c : grid) {
			c.setVisited(false);
		}
		
		panel.repaint();

		/*Set up your data structure with the first cell (grid.get(0))*/
		
		stack =  new Stack<Cell>();
		stack.push(grid.get(0));
		
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
		
		if (!stack.peek().equals(grid.get(grid.size() - 1))) { //If the head node does not contain the last cell continue solving 
			
			Cell currentCell = stack.pop();
			currentCell.setVisited(true); //Popping the element means we have visited
			
			for (Cell cell : currentCell.getValidMoveNeighbours(grid)) {
				if (!cell.isVisited()) {
					
					stack.push(cell);
					cell.setParent(currentCell); //Saves the path
				}
			}
			
			if (currentCell.checkDeadEnd(grid)) { //checkDeadEnd is defined in the Cell class
				currentCell.setDeadEnd(true); 
			}
			
		} else {
			drawPath();
			Maze.solved = true;
			timer.stop();
		}
		
		panel.setCurrent(stack.peek());
		panel.repaint();
		timer.setDelay(Maze.speed);
	}


	/**
	 * Mark each cell in your solved path with cell.setPath(true)
	 */
	private void drawPath() {
		
		//Draws the proper path based on the parent of each cell
		Cell currentCell = stack.peek();
		while (currentCell.getParent() != null) {
			
			currentCell.setPath(true);
			currentCell = currentCell.getParent();
			
		}
	}
}