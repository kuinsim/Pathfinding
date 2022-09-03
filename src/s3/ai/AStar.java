package s3.ai;


import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

import s3.base.S3;
import s3.entities.S3PhysicalEntity;
import s3.util.Pair;
import s3.util.Node;


public class AStar {
	private S3 game;
	private S3PhysicalEntity entity;
	private List <Pair<Integer, Integer>> moves;
	private Node start;
	private Node goal;
	
	public static int pathDistance(double start_x, double start_y, double goal_x, double goal_y,
			S3PhysicalEntity i_entity, S3 the_game) {
		AStar a = new AStar(start_x,start_y,goal_x,goal_y,i_entity,the_game);
		List<Pair<Double, Double>> path = a.computePath();
		if (path!=null) return path.size();
		return -1;
	}

	public AStar(double start_x, double start_y, double goal_x, double goal_y,
			S3PhysicalEntity i_entity, S3 the_game) {
		game = the_game;
		entity = i_entity;
		start = new Node(start_x, start_y);
		goal = new Node(goal_x, goal_y);
		
		moves = new ArrayList <Pair<Integer, Integer>>();
		moves.add(new Pair<Integer, Integer>(-1, 0));
		moves.add(new Pair<Integer, Integer>(1, 0));
		moves.add(new Pair<Integer, Integer>(0, -1));
		moves.add(new Pair<Integer, Integer>(0, 1));
	}

	public List<Pair<Double, Double>> computePath() {
		start.g = 0;
		start.h = start.heuristic(goal);
		ArrayList<Node> open = new ArrayList<Node>();
		open.add(start);
		HashSet<Node> closed = new HashSet<Node>();
		
		while (!open.isEmpty()) {
			Node N = removeLowestF(open);
			
			if ((N.x == goal.x) && (N.y == goal.y)) {
				return retrievePath(N);
			}
			
			closed.add(N);
			
			for (Pair<Integer, Integer> m : moves) {
				double M_x = N.x + m.m_a;
				double M_y = N.y + m.m_b;
				Node M = new Node(M_x, M_y);
				
				if (!open.contains(M) && !closed.contains(M) && isCellFree(M_x, M_y)) {
					M.parent = N;
					M.g = N.g + 1;
					M.h = M.heuristic(goal);
					open.add(M);
				}
			}
		}
		
		return null;
	}
	
	public Node removeLowestF(ArrayList<Node> open) {
		Node N = null;
		double lowest_F = 0;
		
		for (Node n: open) {
			double F = n.g + n.h;
			
			if ((N == null) || (F < lowest_F)) {
				N = n;
				lowest_F = F;
			}
		}
		
		if (N != null) {
			open.remove(N);
		}
		
		return N;
	}
	
	public List<Pair<Double, Double>> retrievePath(Node N) {
		List<Pair<Double, Double>> path = new ArrayList<Pair<Double, Double>>();
		
		while (N != start) {
			Pair<Double, Double> P = new Pair<Double, Double>(N.x, N.y);
			
			path.add(0,P);
			N = N.parent;
		}
		
		return path;
	}
	
	public boolean isCellFree(double x, double y) {
		S3PhysicalEntity E = (S3PhysicalEntity) entity.clone();
		E.setX((int) x);
		E.setY((int) y);
		
		if ((x < 0) || (y < 0) || (x >= game.getMap().getWidth()) || (y >= game.getMap().getHeight())) {
			return false;
		}
		
		else if (game.anyLevelCollision(E) != null) {
			return false;
		}
		
		return true;
	}
}