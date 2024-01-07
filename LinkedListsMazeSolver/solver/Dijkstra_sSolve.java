//Sources:
//Learned how to use a comparator statement from: 
//https://www.callicoder.com/java-priority-queue/

package solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;


import javax.swing.Timer;

import main.*;
import util.Cell;
import java.util.PriorityQueue;

public class Dijkstra_sSolve{
	
	
	private final List<Cell> grid;
	private PriorityQueue<Cell> pQueue;
	private Comparator<Cell> cellWeightComparator;
	
	final Timer timer = new Timer(Maze.speed, null);

	public Dijkstra_sSolve(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		
		for(Cell c : grid) {
			c.setVisited(false);
		}
		
		panel.repaint();

		/*Set up your data structure with the first cell (grid.get(0))*/

		cellWeightComparator = (cell1, cell2) -> { //Comparator based on total weight of the path
			return cell1.getTotalWeight() - cell2.getTotalWeight(); //Total weight is defined in the Cell class
		};
		
		pQueue =  new PriorityQueue<Cell>(cellWeightComparator);
		pQueue.add(grid.get(0));
		
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
		
		if (!pQueue.peek().equals(grid.get(grid.size() - 1))) { //If the head of the stack is not the end
			
			Cell currentCell = pQueue.remove(); 
			currentCell.setVisited(true); //Removing the element means we have visited
			
			for (Cell cell : currentCell.getValidMoveNeighbours(grid)) {
				if (!cell.isVisited()) {
					
					pQueue.add(cell);
					cell.setParent(currentCell); //saves the path
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
		
		panel.setCurrent(pQueue.peek());
		panel.repaint();
		timer.setDelay(Maze.speed);
	}


	/**
	 * Mark each cell in your solved path with cell.setPath(true)
	 */
	private void drawPath() {
		
		//Draws the proper path based on the parent of each cell
		Cell currentCell = pQueue.peek();
		while (currentCell.getParent() != null) {
			
			currentCell.setPath(true);
			currentCell = currentCell.getParent();
			
		}
	}
}