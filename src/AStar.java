import java.util.*;
/*************************************************************************
 *  Author:Michael Marshal, 2015085, w15830009
 *  Last update: 03-04-2017
 *
 *************************************************************************/
public class AStar {
    public static final int DIAGONAL_COST = 14;//1.4*10
    public static final int V_H_COST = 10;//1.0*10 pythogaras theorm 
    
    static class Cell{  
        int heuristicCost = 0; //Heuristic cost
        int finalCost = 0; //G+H
        int i, j;
        Cell parent; 
        
        Cell(int i, int j){
            this.i = i;
            this.j = j; 
        }
        
        @Override
        public String toString(){
            return "["+this.i+", "+this.j+"]";
        }
    }
    
    //Blocked cells are just null Cell values in grid
    static Cell [][] grid = new Cell[5][5];
    
    static PriorityQueue<Cell> open;//priority queue for to conparison
     
    static boolean closed[][];
    static int startI, startJ;
    static int endI, endJ;
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }
    public static void setBlocked(int i, int j){
        grid[i][j] = null;
    }
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                	StdDraw.square(j, N-i-1, .5);
                else StdDraw.filledSquare(j, N-i-1, .5);
    }
    
    public static void setStartCell(int i, int j){
        startI = i;
        startJ = j;
    }
    
    public static void setEndCell(int i, int j){
        endI = i;
        endJ = j; 
    }
    
    static void checkAndUpdateCost(Cell current, Cell t, int cost){
        if(t == null || closed[t.i][t.j])return;
        int t_final_cost = t.heuristicCost+cost;
        
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }
    
    /*
    Params :
    tCase = test case No.
    x, y = Board's dimensions
    si, sj = start location's x and y coordinates
    ei, ej = end location's x and y coordinates
    int[][] blocked = array containing inaccessible cell coordinates
    */
   
	public static String[] first(String method, int x, int y, int si, int sj, int ei, int ej, int[][] blocked) throws InterruptedException {
		// TODO Auto-generated method stub
		 //System.out.println("\n\nTest Case #"+tCase);
         //Reset
        grid = new Cell[x][y];
        closed = new boolean[x][y];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
             Cell c1 = (Cell)o1;
             Cell c2 = (Cell)o2;

             return c1.finalCost<c2.finalCost?-1:
                     c1.finalCost>c2.finalCost?1:0;
         });
        //Set start position
        setStartCell(si, sj);  //Setting to 0,0 by default. Will be useful for the UI part
        
        //Set End Location
        setEndCell(ei, ej); 
       
        for(int i=0;i<x;++i){
           for(int j=0;j<y;++j){
         	  
               grid[i][j] = new Cell(i, j);
               grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
//               System.out.print(grid[i][j].heuristicCost+" ");
           }
//           System.out.println();
        }
        grid[si][sj].finalCost = 0;
        
        /*
          Set blocked cells. Simply set the cell values to null
          for blocked cells.
        */
        for(int i=0;i<blocked.length;++i){
            setBlocked(blocked[i][0], blocked[i][1]);
        }
        
        //Display initial map
        StdDraw.setXscale(-1, x);
        StdDraw.setYscale(-1, y);
        
        System.out.println("Grid: ");
         for(int i=0;i<x;++i){
             for(int j=0;j<y;++j){
                if(i==si&&j==sj){
             	   StdDraw.setPenColor(StdDraw.BLACK);
             	   StdDraw.square(j, x-i-1, .5);
             	   StdDraw.setPenColor(StdDraw.GREEN);
             	   StdDraw.filledCircle(j, x-i-1, .5);
             	   System.out.print("SO  "); //Source
                }
                else if(i==ei && j==ej){
             	   StdDraw.setPenColor(StdDraw.BLACK);
             	   StdDraw.square(j, x-i-1, .5);
             	   StdDraw.setPenColor(StdDraw.RED);
             	   StdDraw.filledCircle(j, x-i-1, .5);
             	   System.out.print("DE  ");  //Destination
                }
                else if(grid[i][j]!=null){
             	   StdDraw.setPenColor(StdDraw.BLACK);
             	   StdDraw.square(j, x-i-1, .5);
             	   System.out.printf("%-3d ", 0);//outline
                }
                else  {
             	   StdDraw.setPenColor(StdDraw.BLACK);
             	   StdDraw.filledSquare(j, x-i-1, .5);
             	   System.out.print("BL  "); //blocked
                }
             }
             System.out.println();
         } 
         System.out.println();
        
        Manhattan();
      
        System.out.println("\nScores for cells: ");
        for(int i=0;i<x;++i){
            for(int j=0;j<x;++j){
                if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].finalCost);
                else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();
        int finalCost=0;
        int gcost=0;
        if(closed[endI][endJ]){
            //Trace back the path 
             System.out.println("Path: ");
             Cell current = grid[endI][endJ];
             System.out.print(current);
             
             while(current.parent!=null){
            	 gcost=finalCost;
            	 finalCost+=current.finalCost;
                 System.out.print(" -> "+current.parent);
                 current = current.parent;
                // Thread.currentThread().sleep(300);
             	StdDraw.setPenColor(StdDraw.BLUE);
          	   StdDraw.filledSquare(current.j, x-current.i-1, .5);
             } 
             System.out.println();
             Stopwatch timerFlow = new Stopwatch();
             StdOut.println("Elapsed time = " + timerFlow.elapsedTime());
             System.out.println();
        }else System.out.println("No possible path");
        System.out.println("final Cost = "+finalCost);
        System.out.println("G Cost = "+gcost);
        return null;
 }
		
	

	public static String[] third(String method, int x, int y, int si, int sj, int ei, int ej, int[][] blocked) throws InterruptedException {
			// TODO Auto-generated method stub
			// System.out.println("\n\nTest Case #"+tCase);
	         //Reset
	        grid = new Cell[x][y];
	        closed = new boolean[x][y];
	        open = new PriorityQueue<>((Object o1, Object o2) -> {
	             Cell c1 = (Cell)o1;
	             Cell c2 = (Cell)o2;

	             return c1.finalCost<c2.finalCost?-1:
	                     c1.finalCost>c2.finalCost?1:0;
	         });
	        //Set start position
	        setStartCell(si, sj);  //Setting to 0,0 by default. Will be useful for the UI part
	        
	        //Set End Location
	        setEndCell(ei, ej); 
	       
	        for(int i=0;i<x;++i){
	           for(int j=0;j<y;++j){
	         	  
	               grid[i][j] = new Cell(i, j);
	               grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
//	               System.out.print(grid[i][j].heuristicCost+" ");
	           }
//	           System.out.println();
	        }
	        grid[si][sj].finalCost = 0;
	        
	        /*
	          Set blocked cells. Simply set the cell values to null
	          for blocked cells.
	        */
	        for(int i=0;i<blocked.length;++i){
	            setBlocked(blocked[i][0], blocked[i][1]);
	        }
	        
	        //Display initial map
	        StdDraw.setXscale(-1, x);
	        StdDraw.setYscale(-1, y);
	        
	        System.out.println("Grid: ");
	         for(int i=0;i<x;++i){
	             for(int j=0;j<y;++j){
	                if(i==si&&j==sj){
	             	   StdDraw.setPenColor(StdDraw.BLACK);
	             	   StdDraw.square(j, x-i-1, .5);
	             	   StdDraw.setPenColor(StdDraw.GREEN);
	             	   StdDraw.filledCircle(j, x-i-1, .5);
	             	   System.out.print("SO  "); //Source
	                }
	                else if(i==ei && j==ej){
	             	   StdDraw.setPenColor(StdDraw.BLACK);
	             	   StdDraw.square(j, x-i-1, .5);
	             	   StdDraw.setPenColor(StdDraw.RED);
	             	   StdDraw.filledCircle(j, x-i-1, .5);
	             	   System.out.print("DE  ");  //Destination
	                }
	                else if(grid[i][j]!=null){
	             	   StdDraw.setPenColor(StdDraw.BLACK);
	             	   StdDraw.square(j, x-i-1, .5);
	             	   System.out.printf("%-3d ", 0);
	                }
	                else {
	             	   StdDraw.setPenColor(StdDraw.BLACK);
	             	   StdDraw.filledSquare(j, x-i-1, .5);
	             	   System.out.print("BL  "); 
	                }
	             }
	             System.out.println();
	         } 
	         System.out.println();
	        
	    
	        Chebyshev();
	        
	        System.out.println("\nScores for cells: ");
	        for(int i=0;i<x;++i){
	            for(int j=0;j<x;++j){
	                if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].finalCost);
	                else System.out.print("BL  ");
	            }
	            System.out.println();
	        }
	        System.out.println();
	        int finalCost=0;
	        int gcost=0;
	        if(closed[endI][endJ]){
	            //Trace back the path 
	             System.out.println("Path: ");
	             Cell current = grid[endI][endJ];
	             System.out.print(current);
	             while(current.parent!=null){
	            	 gcost=finalCost;
	            	 finalCost+=current.finalCost;
	                 System.out.print(" -> "+current.parent);
	                 current = current.parent;
	                // Thread.currentThread().sleep(300);
	                 StdDraw.setPenRadius(0.02);
	             	StdDraw.setPenColor(StdDraw.GREEN);
	          	   StdDraw.line(current.j, x-current.i-1, current.j, x-current.i-1);
	             } 
	             System.out.println();
	             Stopwatch timerFlow = new Stopwatch();
	             StdOut.println("Elapsed time = " + timerFlow.elapsedTime());
	             System.out.println();
	        }else System.out.println("No possible path");
	        System.out.println("final Cost = "+finalCost);
	        System.out.println("G Cost = "+gcost);
			return null;
	 }
	

	public static String[] second(String method, int x, int y, int si, int sj, int ei, int ej, int[][] blocked) throws InterruptedException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		 //System.out.println("\n\nTest Case #"+tCase);
        //Reset
       grid = new Cell[x][y];
       closed = new boolean[x][y];
       open = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell)o1;
            Cell c2 = (Cell)o2;

            return c1.finalCost<c2.finalCost?-1:
                    c1.finalCost>c2.finalCost?1:0;
        });
       //Set start position
       setStartCell(si, sj);  //Setting to 0,0 by default. Will be useful for the UI part
       
       //Set End Location
       setEndCell(ei, ej); 
      
       for(int i=0;i<x;++i){
          for(int j=0;j<y;++j){
        	  
              grid[i][j] = new Cell(i, j);
              grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
//              System.out.print(grid[i][j].heuristicCost+" ");
          }
//          System.out.println();
       }
       grid[si][sj].finalCost = 0;
       
       /*
         Set blocked cells. Simply set the cell values to null
         for blocked cells.
       */
       for(int i=0;i<blocked.length;++i){
           setBlocked(blocked[i][0], blocked[i][1]);
       }
       
       //Display initial map
       StdDraw.setXscale(-1, x);
       StdDraw.setYscale(-1, y);
       
       System.out.println("Grid: ");
        for(int i=0;i<x;++i){
            for(int j=0;j<y;++j){
               if(i==si&&j==sj){
            	   StdDraw.setPenColor(StdDraw.BLACK);
            	   StdDraw.square(j, x-i-1, .5);
            	   StdDraw.setPenColor(StdDraw.GREEN);
            	   StdDraw.filledCircle(j, x-i-1, .5);
            	   System.out.print("SO  "); //Source
               }
               else if(i==ei && j==ej){
            	   StdDraw.setPenColor(StdDraw.BLACK);
            	   StdDraw.square(j, x-i-1, .5);
            	   StdDraw.setPenColor(StdDraw.RED);
            	   StdDraw.filledCircle(j, x-i-1, .5);
            	   System.out.print("DE  ");  //Destination
               }
               else if(grid[i][j]!=null){
            	   StdDraw.setPenColor(StdDraw.BLACK);
            	   StdDraw.square(j, x-i-1, .5);
            	   System.out.printf("%-3d ", 0);
               }
               else {
            	   StdDraw.setPenColor(StdDraw.BLACK);
            	   StdDraw.filledSquare(j, x-i-1, .5);
            	   System.out.print("BL  "); 
               }
            }
            System.out.println();
        } 
        System.out.println();
      
      Euclidean();
      
       
       System.out.println("\nScores for cells: ");
       for(int i=0;i<x;++i){
           for(int j=0;j<x;++j){
               if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].finalCost);
               else System.out.print("BL  ");
           }
           System.out.println();
       }
       System.out.println();
        int finalCost=0;
        int gcost=0;
       if(closed[endI][endJ]){
           //Trace back the path 
            System.out.println("Path: ");
            Cell current = grid[endI][endJ];
            System.out.print(current);
            while(current.parent!=null){
            	gcost=finalCost;
            	finalCost+=current.finalCost;
            	 
                System.out.print(" -> "+current.parent);
                current = current.parent;
               // Thread.currentThread().sleep(300);
                StdDraw.setPenRadius( .02);
            	StdDraw.setPenColor(StdDraw.RED);
          	   StdDraw.line(current.j, x-current.i-1, current.j, x-current.i-1);

         	  // StdDraw.filledSquare(current.j, x-current.i-1, .5);
            } 
            System.out.println();
        //    Stopwatch timerFlow = new Stopwatch();
         //   StdOut.println("Elapsed time = " + timerFlow.elapsedTime());
            System.out.println();
       }else System.out.println("No possible path");
       System.out.println("final Cost = "+finalCost);
       System.out.println("G Cost = "+gcost);

       return null;
	
}
	
	private static void Euclidean(){
		 //add the start location to open list.
        open.add(grid[startI][startJ]);
        
        Cell current;
        
        while(true){ 
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true; 

            if(current.equals(grid[endI][endJ])){
                return; 
            } 

            Cell t;  
            if(current.i-1>=0){
               
                
                if(current.j-1>=0){                      
                    t = grid[current.i-1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }

                if(current.j+1<grid[0].length){
                    t = grid[current.i-1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
                
            }

            

            if(current.i+1<grid.length){
               

               if(current.j-1>=0){
                    t = grid[current.i+1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
                
                if(current.j+1<grid[0].length){
                   t = grid[current.i+1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
                
            }
        
    }
	}
	private static void Manhattan(){
		 //add the start location to open list.
        open.add(grid[startI][startJ]);
        
        Cell current;
        
        while(true){ 
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true; 

            if(current.equals(grid[endI][endJ])){
            	//System.out.println("here");
                return; 
            } 

            Cell t;  
            if(current.i-1>=0){
                t = grid[current.i-1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
                
            
            }

            if(current.j-1>=0){
                t = grid[current.i][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.j+1<grid[0].length){
                t = grid[current.i][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.i+1<grid.length){
                t = grid[current.i+1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

             
          }
        
    }
	}

	private static void Chebyshev() {
		 //add the start location to open list.
        open.add(grid[startI][startJ]);
        
        Cell current;
        
        while(true){ 
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true; 

            if(current.equals(grid[endI][endJ])){
                return; 
            } 

            Cell t;  
            if(current.i-1>=0){
                t = grid[current.i-1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
                
                if(current.j-1>=0){                      
                    t = grid[current.i-1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }

                if(current.j+1<grid[0].length){
                    t = grid[current.i-1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
                
            }

            if(current.j-1>=0){
                t = grid[current.i][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.j+1<grid[0].length){
                t = grid[current.i][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.i+1<grid.length){
                t = grid[current.i+1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

               if(current.j-1>=0){
                    t = grid[current.i+1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
                
                if(current.j+1<grid[0].length){
                   t = grid[current.i+1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
                
            }
        
    }
	}
}