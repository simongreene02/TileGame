package com.greatworksinc.tilegame.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class MazeGenerator {

	public static void main(String[] args) {
		final int height = 11;
		final int width = 11;
		
		Random random = new Random(0);
		boolean[][] maze = new boolean[height][width];
		Point cursor = new Point();
		int direction;
		maze[random.nextInt(height/2)*2][random.nextInt(width/2)*2] = true;
		
		
		do {
			do {
				cursor.setLocation(random.nextInt(width/2)*2, random.nextInt(height/2)*2);
				direction = random.nextInt(4) * 90;
			} while (isTileInDirection(cursor, direction, maze) || !maze[cursor.y][cursor.x]);
			boolean isForward = !isTileInDirection(cursor, direction, maze);
			boolean isLeft = !isTileInDirection(cursor, direction-90, maze);
			boolean isRight = !isTileInDirection(cursor, direction+90, maze);
			while (isForward || isLeft || isRight) {
				if (!isForward || random.nextInt(2) == 0) {
					if (isLeft && isRight) {
						if (random.nextBoolean()) {
							direction -= 90;
						} else {
							direction += 90;
						}
					} else if (isLeft) {
						direction -= 90;
					} else if (isRight) {
						direction += 90;
					}
					direction = direction % 360;
				}
				int x = cursor.x + (int) Math.cos(Math.toRadians(direction));
				int y = cursor.y + (int) Math.sin(Math.toRadians(direction));
				maze[y][x] = true;
				x = cursor.x + (int) Math.cos(Math.toRadians(direction)) * 2;
				y = cursor.y + (int) Math.sin(Math.toRadians(direction)) * 2;
				maze[y][x] = true;
				cursor.setLocation(x, y);
				isForward = !isTileInDirection(cursor, direction, maze);
				isLeft = !isTileInDirection(cursor, direction-90, maze);
				isRight = !isTileInDirection(cursor, direction+90, maze);
			}
		} while (!isMazeFinished(maze));
		
		for (boolean[] row : maze) {
			for (boolean tile : row) {
				//System.out.print(tile ? "-" : "+");
				System.out.print(tile ? "15," : "75,");
			}
			System.out.println();
		}
		
//		drawImage(maze);
	}

	private static boolean isTileInDirection(Point cursor, int direction, boolean[][] maze) {
		int x = cursor.x + (int) Math.cos(Math.toRadians(direction)) * 2;
		int y = cursor.y + (int) Math.sin(Math.toRadians(direction)) * 2;
		if (y >= 0 && y < maze.length && x >= 0 && x < maze[0].length) {
			return maze[y][x];
		} else {
			return true;
		}
	}
	
	private static boolean isMazeFinished(boolean[][] maze) {
		for (int i = 0; i <= maze.length; i += 2) {
			for (int j = 0; j <= maze[0].length; j += 2) {
				if (!maze[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	private static void drawImage(boolean[][] maze) {
		BufferedImage img = new BufferedImage(maze.length, maze[0].length, BufferedImage.TYPE_INT_RGB);
		Graphics2D canvas = img.createGraphics();
		System.out.println("Created Canvas.");
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				canvas.setColor(maze[i][j] ? Color.WHITE : Color.BLACK);
				canvas.drawRect(i, j, 1, 1);
			}
		}
		System.out.println("Drew Pattern to Canvas.");
		File outputfile = new File("saved.png");
		try {
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Wrote to File.");
	}

}
