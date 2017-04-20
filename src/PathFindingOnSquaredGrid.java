import java.util.*;
/*************************************************************************
 *  Author:Michael Marshal, 2015085, w15830009
 *  Last update: 03-04-2017
 *
 *************************************************************************/
public class PathFindingOnSquaredGrid {

	 // given an N-by-N matrix of open cells, return an N-by-N matrix
   // of cells reachable from the top
   public static boolean[][] flow(boolean[][] open) {
       int N = open.length;
   
       boolean[][] full = new boolean[N][N];
       for (int j = 0; j < N; j++) {
           flow(open, full, 0, j);
       }
   	
       return full;
   }
   
   // determine set of open/blocked cells using depth first search
   public static void flow(boolean[][] open, boolean[][] full, int i, int j) {
       int N = open.length;

       // base cases
       if (i < 0 || i >= N) return;    // invalid row
       if (j < 0 || j >= N) return;    // invalid column
       if (!open[i][j]) return;        // not an open cell
       if (full[i][j]) return;         // already marked as open

     /*  full[i][j] = true;

       flow(open, full, i+1, j);   // down
       flow(open, full, i, j+1);   // right
       flow(open, full, i, j-1);   // left
       flow(open, full, i-1, j);   // up
*/   }

   // does the system percolate?
   public static boolean percolates(boolean[][] open) {
       int N = open.length;
   	
       boolean[][] full = flow(open);
       for (int j = 0; j < N; j++) {
           if (full[N-1][j]) return true;
       }
   	
       return false;
   }
   
// does the system percolate vertically in a direct way?
   public static boolean percolatesDirect(boolean[][] open) {
       int N = open.length;
   	
       boolean[][] full = flow(open);
       int directPerc = 0;
       for (int j = 0; j < N; j++) {
       	if (full[N-1][j]) {
       		// StdOut.println("Hello");
       		directPerc = 1;
       		int rowabove = N-2;
       		for (int i = rowabove; i >= 0; i--) {
       			if (full[i][j]) {
       				// StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
       				directPerc++;
       			}
       			else break;
       		}
       	}
       }
   	
       // StdOut.println("Direct Percolation is: " + directPerc);
       if (directPerc == N) return true; 
       else return false;
   }
   
   // draw the N-by-N boolean matrix to standard draw
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

   // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
   public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2) {
       int N = a.length;
       StdDraw.setXscale(-1, N);;
       StdDraw.setYscale(-1, N);
       StdDraw.setPenColor(StdDraw.BLACK);
       for (int i = 0; i < N; i++)
           for (int j = 0; j < N; j++)
               if (a[i][j] == which)
               	if ((i == x1 && j == y1) ||(i == x2 && j == y2)) {
               		StdDraw.circle(j, N-i-1, .5);
               	}
               	else StdDraw.square(j, N-i-1, .5);
               else StdDraw.filledSquare(j, N-i-1, .5);
   }
   
   // return a random N-by-N boolean matrix, where each entry is
   // true with probability p
   public static boolean[][] random(int N, double p) {
       boolean[][] a = new boolean[N][N];
       for (int i = 0; i < N; i++)
           for (int j = 0; j < N; j++)
               a[i][j] = StdRandom.bernoulli(p);
       return a;
   }
   

   // test client
   public static void main(String[] args) throws Exception {
       // boolean[][] open = StdArrayIO.readBoolean2D();
   	
   	// The following will generate a 10x10 squared grid with relatively few obstacles in it
   	// The lower the second parameter, the more obstacles (black cells) are generated
   	boolean[][] randomlyGenMatrix = random(10, 0.6);
   	int size=10;
   	StdArrayIO.print(randomlyGenMatrix);
   	show(randomlyGenMatrix, true);
   	
   	int blockedlength=0;
   	for(int i=0;i<randomlyGenMatrix.length;i++){
   		for(int j=0;j<randomlyGenMatrix.length;j++){
   			if(randomlyGenMatrix[i][j]==false){
   				blockedlength++;
   			}
   		}
   	}
   	int blokedno=0;
   	int[][]blocked= new int[blockedlength][2];
   	for(int i=0;i<randomlyGenMatrix.length;i++){
   		for(int j=0;j<randomlyGenMatrix.length;j++){
   			if(randomlyGenMatrix[i][j]==false){
   				blocked[blokedno][0]=i;
   				blocked[blokedno][1]=j;
   				blokedno++;
   			}
   		}
   	}
   	System.out.println();
   	System.out.println("The system percolates: " + percolates(randomlyGenMatrix));
   	
   	System.out.println();
   	System.out.println("The system percolates directly: " + percolatesDirect(randomlyGenMatrix));
   	System.out.println();
   	
   	// Reading the coordinates for points A and B on the input squared grid.
   	
   	// THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
   	// Start the clock ticking in order to capture the time being spent on inputting the coordinates
   	// You should position this command accordingly in order to perform the algorithmic analysis
   
   	
   	Scanner in = new Scanner(System.in);
   	System.out.println("Select one of the following order");
	System.out.println("1.Manhattan Distance");
	System.out.println("2.Euclidean Distance");
	System.out.println("3.Chebyshev Distance");
	
	int input=in.nextInt();
	System.out.println("Enter i for A > ");
    int Ai = in.nextInt();
    
    System.out.println("Enter j for A > ");
    int Aj = in.nextInt();
    
    System.out.println("Enter i for B > ");
    int Bi = in.nextInt();
    
    System.out.println("Enter j for B > ");
    int Bj = in.nextInt();
    // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
       // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
   	// You should position this command accordingly in order to perform the algorithmic analysis
   	
       
       
       
       show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);
       
       AStar algorithm = new AStar();
       Stopwatch timerFlow1 = new Stopwatch();
  		String[] finalArray;
		String path;
		char[] arrayPath;
		if(input==1){
			try {
				String method="manhattan";
				Stopwatch timerFlow2 = new Stopwatch();
				finalArray = algorithm.first(method, size, size, Bi, Bj, Ai, Aj, blocked);
				StdOut.println("Elapsed time = " + timerFlow2.elapsedTime());
				path = finalArray[0];
				arrayPath = path.toCharArray();
				for(int i=0; i<arrayPath.length;i+=2){
					show(randomlyGenMatrix,Character.getNumericValue(arrayPath[i]),
							Character.getNumericValue(arrayPath[i+1]));
				}
				show(randomlyGenMatrix,true,Ai,Aj,Bi,Bj);
				//algorithm.first(method, size, size, Bi, Bj, Ai, Aj, blocked);
				StdOut.println("Elapsed time = " + timerFlow1.elapsedTime());
		
			} catch (Exception e) {
				
			}
			System.out.println("Select one of the following order");
			System.out.println("1.Manhattan Distance");
			System.out.println("2.Euclidean Distance");
			System.out.println("3.Chebyshev Distance");
			
			int input1=in.nextInt();
			System.out.println("Enter i for A > ");
		    int Ai1 = in.nextInt();
		    
		    System.out.println("Enter j for A > ");
		    int Aj1 = in.nextInt();
		    
		    System.out.println("Enter i for B > ");
		    int Bi1 = in.nextInt();
		    
		    System.out.println("Enter j for B > ");
		    int Bj1 = in.nextInt();
		    // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
		       // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
		   	// You should position this command accordingly in order to perform the algorithmic analysis
		   	
		       
		       
		       
		       show(randomlyGenMatrix, true, Ai1, Aj1, Bi1, Bj1);
		       
		       AStar algorithm1 = new AStar();
		       Stopwatch timerFlow4 = new Stopwatch();
		  		String[] finalArray1;
				String path1;
				char[] arrayPath1;
				if(input1==1){
					try {
						String method1="manhattan";
						Stopwatch timerFlow5 = new Stopwatch();
						finalArray1 = algorithm1.first(method1, size, size, Bi1, Bj1, Ai1, Aj1, blocked);
						StdOut.println("Elapsed time = " + timerFlow5.elapsedTime());
						path1 = finalArray1[0];
						arrayPath1 = path1.toCharArray();
						for(int i=0; i<arrayPath1.length;i+=2){
							show(randomlyGenMatrix,Character.getNumericValue(arrayPath1[i]),
									Character.getNumericValue(arrayPath1[i+1]));
						}
						show(randomlyGenMatrix,true,Ai1,Aj1,Bi1,Bj1);
						//algorithm.first(method, size, size, Bi, Bj, Ai, Aj, blocked);
						StdOut.println("Elapsed time = " + timerFlow5.elapsedTime());
					} catch (Exception e) {
						
					}
				}else if(input1==2){

			   		 try {
			   			Stopwatch timerFlow6 = new Stopwatch();
						finalArray1= algorithm1.second("euclidean", size, size, Bi1, Bj1, Ai1, Aj1, blocked);
						StdOut.println("Elapsed time = " + timerFlow6.elapsedTime());
						 path1= finalArray1[0];
						 arrayPath1= path1.toCharArray();
						for(int i=0; i<arrayPath1.length;i+=2){
							show(randomlyGenMatrix,Character.getNumericValue(arrayPath1[i]),
									Character.getNumericValue(arrayPath1[i+1]));
						}
						show(randomlyGenMatrix,true,Ai1,Aj1,Bi1,Bj1);
						StdOut.println("Elapsed time = " + timerFlow1.elapsedTime());
					} catch (Exception e) {
						
					}System.out.println("Select one of the following order");
					System.out.println("1.Manhattan Distance");
					System.out.println("2.Euclidean Distance");
					System.out.println("3.Chebyshev Distance");
					
					int input3=in.nextInt();
					System.out.println("Enter i for A > ");
				    int Ai3 = in.nextInt();
				    
				    System.out.println("Enter j for A > ");
				    int Aj3 = in.nextInt();
				    
				    System.out.println("Enter i for B > ");
				    int Bi3 = in.nextInt();
				    
				    System.out.println("Enter j for B > ");
				    int Bj3 = in.nextInt();
				    // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
				       // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
				   	// You should position this command accordingly in order to perform the algorithmic analysis
				   	
				       
				       
				       
				       show(randomlyGenMatrix, true, Ai3, Aj3, Bi3, Bj3);
				       
				       AStar algorithm3 = new AStar();
				       //Stopwatch timerFlow = new Stopwatch();
				  		String[] finalArray3;
						String path3;
						char[] arrayPath3;
						if(input3==1){
							try {
								String method1="manhattan";
								Stopwatch timerFlow8 = new Stopwatch();
								finalArray3 = algorithm3.first(method1, size, size, Bi3, Bj3, Ai3, Aj3, blocked);
								StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
								path3 = finalArray3[0];
								arrayPath3 = path3.toCharArray();
								for(int i=0; i<arrayPath3.length;i+=2){
									show(randomlyGenMatrix,Character.getNumericValue(arrayPath3[i]),
											Character.getNumericValue(arrayPath3[i+1]));
								}
								show(randomlyGenMatrix,true,Ai3,Aj3,Bi3,Bj3);
								//algorithm.first(method, size, size, Bi, Bj, Ai, Aj, blocked);
								StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
							} catch (Exception e) {
								
							}
						}else if(input3==2){

					   		 try {
					   			Stopwatch timerFlow9 = new Stopwatch();
								finalArray3= algorithm3.second("euclidean", size, size, Bi3, Bj3, Ai3, Aj3, blocked);
								//StdOut.println("Elapsed time = " + timerFlow6.elapsedTime());
								 path3= finalArray3[0];
								 arrayPath3= path3.toCharArray();
								for(int i=0; i<arrayPath3.length;i+=2){
									show(randomlyGenMatrix,Character.getNumericValue(arrayPath3[i]),
											Character.getNumericValue(arrayPath3[i+1]));
								}
								show(randomlyGenMatrix,true,Ai3,Aj3,Bi3,Bj3);
								StdOut.println("Elapsed time = " + timerFlow9.elapsedTime());
							} catch (Exception e) {
								
							}
					   	
						}else if(input3==3){
							try {
								Stopwatch timerFlow9 = new Stopwatch();
								finalArray3= algorithm3.third("chebyshev", size, size, Bi3, Bj3, Ai3, Aj3, blocked);
								//StdOut.println("Elapsed time = " + timerFlow7.elapsedTime());
								 path3= finalArray3[10];
								 arrayPath3= path3.toCharArray();
								for(int i=0; i<arrayPath3.length;i+=2){
									show(randomlyGenMatrix,Character.getNumericValue(arrayPath3[i]),
											Character.getNumericValue(arrayPath3[i+1]));
								}
								show(randomlyGenMatrix,true,Ai3,Aj3,Bi3,Bj3);
								StdOut.println("Elapsed time = " + timerFlow9.elapsedTime());
							} catch (Exception e) {
								
							}
			   	
				}else if(input3==3){
					try {
						Stopwatch timerFlow7 = new Stopwatch();
						finalArray= algorithm1.third("chebyshev", size, size, Bi1, Bj1, Ai1, Aj1, blocked);
						StdOut.println("Elapsed time = " + timerFlow7.elapsedTime());
						 path1= finalArray[10];
						 arrayPath1= path1.toCharArray();
						for(int i=0; i<arrayPath1.length;i+=2){
							show(randomlyGenMatrix,Character.getNumericValue(arrayPath1[i]),
									Character.getNumericValue(arrayPath1[i+1]));
						}
						show(randomlyGenMatrix,true,Ai1,Aj1,Bi1,Bj1);
						StdOut.println("Elapsed time = " + timerFlow7.elapsedTime());
					} catch (Exception e) {
						
					}
					System.out.println("Select one of the following order");
					System.out.println("1.Manhattan Distance");
					System.out.println("2.Euclidean Distance");
					System.out.println("3.Chebyshev Distance");
					
					int input5=in.nextInt();
					System.out.println("Enter i for A > ");
				    int Ai5 = in.nextInt();
				    
				    System.out.println("Enter j for A > ");
				    int Aj5 = in.nextInt();
				    
				    System.out.println("Enter i for B > ");
				    int Bi5 = in.nextInt();
				    
				    System.out.println("Enter j for B > ");
				    int Bj5 = in.nextInt();
				    // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
				       // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
				   	// You should position this command accordingly in order to perform the algorithmic analysis
				   	
				       
				       
				       
				       show(randomlyGenMatrix, true, Ai5, Aj5, Bi5, Bj5);
				       
				       AStar algorithm5 = new AStar();
				       //Stopwatch timerFlow = new Stopwatch();
				  		String[] finalArray5;
						String path5;
						char[] arrayPath5;
						if(input5==1){
							try {
								String method1="manhattan";
								Stopwatch timerFlow8 = new Stopwatch();
								finalArray5 = algorithm5.first(method1, size, size, Bi5, Bj5, Ai5, Aj5, blocked);
								StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
								path5 = finalArray5[0];
								arrayPath5 = path5.toCharArray();
								for(int i=0; i<arrayPath5.length;i+=2){
									show(randomlyGenMatrix,Character.getNumericValue(arrayPath5[i]),
											Character.getNumericValue(arrayPath5[i+1]));
								}
								show(randomlyGenMatrix,true,Ai5,Aj5,Bi5,Bj5);
								//algorithm.first(method, size, size, Bi, Bj, Ai, Aj, blocked);
								StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
							} catch (Exception e) {
								
							}
						}else if(input3==2){

					   		 try {
					   			Stopwatch timerFlow9 = new Stopwatch();
								finalArray5= algorithm5.second("euclidean", size, size, Bi5, Bj5, Ai5, Aj5, blocked);
								//StdOut.println("Elapsed time = " + timerFlow6.elapsedTime());
								 path5= finalArray5[0];
								 arrayPath5= path5.toCharArray();
								for(int i=0; i<arrayPath5.length;i+=2){
									show(randomlyGenMatrix,Character.getNumericValue(arrayPath5[i]),
											Character.getNumericValue(arrayPath5[i+1]));
								}
								show(randomlyGenMatrix,true,Ai5,Aj5,Bi5,Bj5);
								StdOut.println("Elapsed time = " + timerFlow9.elapsedTime());
							} catch (Exception e) {
								
							}
					   	
						}else if(input3==3){
							try {
								Stopwatch timerFlow9 = new Stopwatch();
								finalArray5= algorithm5.third("chebyshev", size, size, Bi5, Bj5, Ai5, Aj5, blocked);
								//StdOut.println("Elapsed time = " + timerFlow7.elapsedTime());
								 path5= finalArray5[10];
								 arrayPath5= path5.toCharArray();
								for(int i=0; i<arrayPath5.length;i+=2){
									show(randomlyGenMatrix,Character.getNumericValue(arrayPath5[i]),
											Character.getNumericValue(arrayPath5[i+1]));
								}
								show(randomlyGenMatrix,true,Ai5,Aj5,Bi5,Bj5);
								StdOut.println("Elapsed time = " + timerFlow9.elapsedTime());
							} catch (Exception e) {
								
							}
						}
				}
				}
		}else if(input==2){

	   		 try {
	   			Stopwatch timerFlow8 = new Stopwatch();
				finalArray= algorithm.second("euclidean", size, size, Bi, Bj, Ai, Aj, blocked);
				StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
				 path= finalArray[0];
				 arrayPath= path.toCharArray();
				for(int i=0; i<arrayPath.length;i+=2){
					show(randomlyGenMatrix,Character.getNumericValue(arrayPath[i]),
							Character.getNumericValue(arrayPath[i+1]));
				}
				show(randomlyGenMatrix,true,Ai,Aj,Bi,Bj);
				StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
			} catch (Exception e) {
				
			}
	   		System.out.println("Select one of the following order");
			System.out.println("1.Manhattan Distance");
			System.out.println("2.Euclidean Distance");
			System.out.println("3.Chebyshev Distance");
			
			int input2=in.nextInt();
			System.out.println("Enter i for A > ");
		    int Ai2 = in.nextInt();
		    
		    System.out.println("Enter j for A > ");
		    int Aj2 = in.nextInt();
		    
		    System.out.println("Enter i for B > ");
		    int Bi2 = in.nextInt();
		    
		    System.out.println("Enter j for B > ");
		    int Bj2 = in.nextInt();
		    // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
		       // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
		   	// You should position this command accordingly in order to perform the algorithmic analysis
		   	
		       
		       
		       
		       show(randomlyGenMatrix, true, Ai2, Aj2, Bi2, Bj2);
		       
		       AStar algorithm2 = new AStar();
		       //Stopwatch timerFlow = new Stopwatch();
		  		String[] finalArray2;
				String path2;
				char[] arrayPath2;
				if(input2==1){
					try {
						String method1="manhattan";
						Stopwatch timerFlow8 = new Stopwatch();
						finalArray2 = algorithm2.first(method1, size, size, Bi2, Bj2, Ai2, Aj2, blocked);
						StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
						path2 = finalArray2[0];
						arrayPath2 = path2.toCharArray();
						for(int i=0; i<arrayPath2.length;i+=2){
							show(randomlyGenMatrix,Character.getNumericValue(arrayPath2[i]),
									Character.getNumericValue(arrayPath2[i+1]));
						}
						show(randomlyGenMatrix,true,Ai2,Aj2,Bi2,Bj2);
						//algorithm.first(method, size, size, Bi, Bj, Ai, Aj, blocked);
						StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
					} catch (Exception e) {
						
					}
				}else if(input2==2){

			   		 try {
			   			Stopwatch timerFlow9 = new Stopwatch();
						finalArray2= algorithm2.second("euclidean", size, size, Bi2, Bj2, Ai2, Aj2, blocked);
						//StdOut.println("Elapsed time = " + timerFlow6.elapsedTime());
						 path2= finalArray2[0];
						 arrayPath2= path2.toCharArray();
						for(int i=0; i<arrayPath2.length;i+=2){
							show(randomlyGenMatrix,Character.getNumericValue(arrayPath2[i]),
									Character.getNumericValue(arrayPath2[i+1]));
						}
						show(randomlyGenMatrix,true,Ai2,Aj2,Bi2,Bj2);
						StdOut.println("Elapsed time = " + timerFlow9.elapsedTime());
					} catch (Exception e) {
						
					}
			   	
				}else if(input2==3){
					try {
						Stopwatch timerFlow7 = new Stopwatch();
						finalArray2= algorithm2.third("chebyshev", size, size, Bi2, Bj2, Ai2, Aj2, blocked);
						StdOut.println("Elapsed time = " + timerFlow7.elapsedTime());
						 path2= finalArray2[10];
						 arrayPath2= path2.toCharArray();
						for(int i=0; i<arrayPath2.length;i+=2){
							show(randomlyGenMatrix,Character.getNumericValue(arrayPath2[i]),
									Character.getNumericValue(arrayPath2[i+1]));
						}
						show(randomlyGenMatrix,true,Ai2,Aj2,Bi2,Bj2);
						StdOut.println("Elapsed time = " + timerFlow7.elapsedTime());
					} catch (Exception e) {
						
					}
					System.out.println("Select one of the following order");
					System.out.println("1.Manhattan Distance");
					System.out.println("2.Euclidean Distance");
					System.out.println("3.Chebyshev Distance");
					
					int input3=in.nextInt();
					System.out.println("Enter i for A > ");
				    int Ai3 = in.nextInt();
				    
				    System.out.println("Enter j for A > ");
				    int Aj3 = in.nextInt();
				    
				    System.out.println("Enter i for B > ");
				    int Bi3 = in.nextInt();
				    
				    System.out.println("Enter j for B > ");
				    int Bj3 = in.nextInt();
				    // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
				       // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
				   	// You should position this command accordingly in order to perform the algorithmic analysis
				   	
				       
				       
				       
				       show(randomlyGenMatrix, true, Ai3, Aj3, Bi3, Bj3);
				       
				       AStar algorithm3 = new AStar();
				       //Stopwatch timerFlow = new Stopwatch();
				  		String[] finalArray3;
						String path3;
						char[] arrayPath3;
						if(input3==1){
							try {
								String method1="manhattan";
								Stopwatch timerFlow8 = new Stopwatch();
								finalArray3 = algorithm3.first(method1, size, size, Bi3, Bj3, Ai3, Aj3, blocked);
								StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
								path3 = finalArray3[0];
								arrayPath3 = path3.toCharArray();
								for(int i=0; i<arrayPath3.length;i+=2){
									show(randomlyGenMatrix,Character.getNumericValue(arrayPath3[i]),
											Character.getNumericValue(arrayPath3[i+1]));
								}
								show(randomlyGenMatrix,true,Ai3,Aj3,Bi3,Bj3);
								//algorithm.first(method, size, size, Bi, Bj, Ai, Aj, blocked);
								StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
							} catch (Exception e) {
								
							}
						}else if(input3==2){

					   		 try {
					   			Stopwatch timerFlow9 = new Stopwatch();
								finalArray3= algorithm3.second("euclidean", size, size, Bi3, Bj3, Ai3, Aj3, blocked);
								//StdOut.println("Elapsed time = " + timerFlow6.elapsedTime());
								 path3= finalArray3[0];
								 arrayPath3= path3.toCharArray();
								for(int i=0; i<arrayPath3.length;i+=2){
									show(randomlyGenMatrix,Character.getNumericValue(arrayPath3[i]),
											Character.getNumericValue(arrayPath3[i+1]));
								}
								show(randomlyGenMatrix,true,Ai3,Aj3,Bi3,Bj3);
								StdOut.println("Elapsed time = " + timerFlow9.elapsedTime());
							} catch (Exception e) {
								
							}
					   	
						}else if(input3==3){
							try {
								Stopwatch timerFlow9 = new Stopwatch();
								finalArray3= algorithm3.third("chebyshev", size, size, Bi3, Bj3, Ai3, Aj3, blocked);
								//StdOut.println("Elapsed time = " + timerFlow7.elapsedTime());
								 path3= finalArray3[10];
								 arrayPath3= path3.toCharArray();
								for(int i=0; i<arrayPath3.length;i+=2){
									show(randomlyGenMatrix,Character.getNumericValue(arrayPath3[i]),
											Character.getNumericValue(arrayPath3[i+1]));
								}
								show(randomlyGenMatrix,true,Ai3,Aj3,Bi3,Bj3);
								StdOut.println("Elapsed time = " + timerFlow9.elapsedTime());
							} catch (Exception e) {
								
							}
						}
				}
	   	
		}else if(input==3){
			try {
				Stopwatch timerFlow3 = new Stopwatch();
				finalArray= algorithm.third("chebyshev", size, size, Bi, Bj, Ai, Aj, blocked);
				StdOut.println("Elapsed time = " + timerFlow3.elapsedTime());
				 path= finalArray[0];
				 arrayPath= path.toCharArray();
				for(int i=0; i<arrayPath.length;i+=2){
					show(randomlyGenMatrix,Character.getNumericValue(arrayPath[i]),
							Character.getNumericValue(arrayPath[i+1]));
				}
				show(randomlyGenMatrix,true,Ai,Aj,Bi,Bj);
				StdOut.println("Elapsed time = " + timerFlow1.elapsedTime());
			} catch (Exception e) {
				
			}
			System.out.println("Select one of the following order");
			System.out.println("1.Manhattan Distance");
			System.out.println("2.Euclidean Distance");
			System.out.println("3.Chebyshev Distance");
			
			int input3=in.nextInt();
			System.out.println("Enter i for A > ");
		    int Ai3 = in.nextInt();
		    
		    System.out.println("Enter j for A > ");
		    int Aj3 = in.nextInt();
		    
		    System.out.println("Enter i for B > ");
		    int Bi3 = in.nextInt();
		    
		    System.out.println("Enter j for B > ");
		    int Bj3 = in.nextInt();
		    // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
		       // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
		   	// You should position this command accordingly in order to perform the algorithmic analysis
		   	
		       
		       
		       
		       show(randomlyGenMatrix, true, Ai3, Aj3, Bi3, Bj3);
		       
		       AStar algorithm3 = new AStar();
		       //Stopwatch timerFlow = new Stopwatch();
		  		String[] finalArray3;
				String path3;
				char[] arrayPath3;
				if(input3==1){
					try {
						String method1="manhattan";
						Stopwatch timerFlow8 = new Stopwatch();
						finalArray3 = algorithm3.first(method1, size, size, Bi3, Bj3, Ai3, Aj3, blocked);
						StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
						path3 = finalArray3[0];
						arrayPath3 = path3.toCharArray();
						for(int i=0; i<arrayPath3.length;i+=2){
							show(randomlyGenMatrix,Character.getNumericValue(arrayPath3[i]),
									Character.getNumericValue(arrayPath3[i+1]));
						}
						show(randomlyGenMatrix,true,Ai3,Aj3,Bi3,Bj3);
						//algorithm.first(method, size, size, Bi, Bj, Ai, Aj, blocked);
						StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
					} catch (Exception e) {
						
					}
				}else if(input3==2){

			   		 try {
			   			Stopwatch timerFlow9 = new Stopwatch();
						finalArray3= algorithm3.second("euclidean", size, size, Bi3, Bj3, Ai3, Aj3, blocked);
						//StdOut.println("Elapsed time = " + timerFlow6.elapsedTime());
						 path3= finalArray3[0];
						 arrayPath3= path3.toCharArray();
						for(int i=0; i<arrayPath3.length;i+=2){
							show(randomlyGenMatrix,Character.getNumericValue(arrayPath3[i]),
									Character.getNumericValue(arrayPath3[i+1]));
						}
						show(randomlyGenMatrix,true,Ai3,Aj3,Bi3,Bj3);
						StdOut.println("Elapsed time = " + timerFlow9.elapsedTime());
					} catch (Exception e) {
						
					}
			   	
				}else if(input3==3){
					try {
						Stopwatch timerFlow9 = new Stopwatch();
						finalArray3= algorithm3.third("chebyshev", size, size, Bi3, Bj3, Ai3, Aj3, blocked);
						//StdOut.println("Elapsed time = " + timerFlow7.elapsedTime());
						 path3= finalArray3[10];
						 arrayPath3= path3.toCharArray();
						for(int i=0; i<arrayPath3.length;i+=2){
							show(randomlyGenMatrix,Character.getNumericValue(arrayPath3[i]),
									Character.getNumericValue(arrayPath3[i+1]));
						}
						show(randomlyGenMatrix,true,Ai3,Aj3,Bi3,Bj3);
						StdOut.println("Elapsed time = " + timerFlow9.elapsedTime());
					} catch (Exception e) {
						
					}
					System.out.println("Select one of the following order");
					System.out.println("1.Manhattan Distance");
					System.out.println("2.Euclidean Distance");
					System.out.println("3.Chebyshev Distance");
					
					int input4=in.nextInt();
					System.out.println("Enter i for A > ");
				    int Ai4 = in.nextInt();
				    
				    System.out.println("Enter j for A > ");
				    int Aj4 = in.nextInt();
				    
				    System.out.println("Enter i for B > ");
				    int Bi4 = in.nextInt();
				    
				    System.out.println("Enter j for B > ");
				    int Bj4 = in.nextInt();
				    // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
				       // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
				   	// You should position this command accordingly in order to perform the algorithmic analysis
				   	
				       
				       
				       
				       show(randomlyGenMatrix, true, Ai3, Aj3, Bi3, Bj3);
				       
				       AStar algorithm4 = new AStar();
				       //Stopwatch timerFlow = new Stopwatch();
				  		String[] finalArray4;
						String path4;
						char[] arrayPath4;
						if(input3==1){
							try {
								String method1="manhattan";
								Stopwatch timerFlow8 = new Stopwatch();
								finalArray4 = algorithm4.first(method1, size, size, Bi4, Bj4, Ai4, Aj4, blocked);
								StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
								path4 = finalArray4[0];
								arrayPath4 = path4.toCharArray();
								for(int i=0; i<arrayPath4.length;i+=2){
									show(randomlyGenMatrix,Character.getNumericValue(arrayPath4[i]),
											Character.getNumericValue(arrayPath4[i+1]));
								}
								show(randomlyGenMatrix,true,Ai4,Aj4,Bi4,Bj4);
								//algorithm.first(method, size, size, Bi, Bj, Ai, Aj, blocked);
								StdOut.println("Elapsed time = " + timerFlow8.elapsedTime());
							} catch (Exception e) {
								
							}
						}else if(input3==2){

					   		 try {
					   			Stopwatch timerFlow9 = new Stopwatch();
								finalArray4= algorithm4.second("euclidean", size, size, Bi4, Bj4, Ai4, Aj4, blocked);
								//StdOut.println("Elapsed time = " + timerFlow6.elapsedTime());
								 path4= finalArray4[0];
								 arrayPath4= path4.toCharArray();
								for(int i=0; i<arrayPath4.length;i+=2){
									show(randomlyGenMatrix,Character.getNumericValue(arrayPath4[i]),
											Character.getNumericValue(arrayPath4[i+1]));
								}
								show(randomlyGenMatrix,true,Ai4,Aj4,Bi4,Bj4);
								StdOut.println("Elapsed time = " + timerFlow9.elapsedTime());
							} catch (Exception e) {
								
							}
					   	
						}else if(input3==3){
							try {
								Stopwatch timerFlow9 = new Stopwatch();
								finalArray4= algorithm4.third("chebyshev", size, size, Bi4, Bj4, Ai4, Aj4, blocked);
								//StdOut.println("Elapsed time = " + timerFlow7.elapsedTime());
								 path4= finalArray4[10];
								 arrayPath4= path4.toCharArray();
								for(int i=0; i<arrayPath4.length;i+=2){
									show(randomlyGenMatrix,Character.getNumericValue(arrayPath4[i]),
											Character.getNumericValue(arrayPath4[i+1]));
								}
								show(randomlyGenMatrix,true,Ai4,Aj4,Bi4,Bj4);
								StdOut.println("Elapsed time = " + timerFlow9.elapsedTime());
							} catch (Exception e) {
								
							}
						}
				
				}
		}
		
		
      /* switch(input){
   	case 1:
   		
   	   
		try {
			String method="manhattan";
			finalArray = algorithm.first(method, size, size, Bi, Bj, Ai, Aj, blocked);
			path = finalArray[0];
			arrayPath = path.toCharArray();
			for(int i=0; i<arrayPath.length;i+=2){
				show(randomlyGenMatrix,Character.getNumericValue(arrayPath[i]),
						Character.getNumericValue(arrayPath[i+1]));
			}
			show(randomlyGenMatrix,true,Ai,Aj,Bi,Bj);
			//algorithm.first(method, size, size, Bi, Bj, Ai, Aj, blocked);
			StdOut.println("Elapsed time = " + timerFlow1.elapsedTime());
		} catch (Exception e) {
			
		}
   	
   	break;
   	case 2:
   		
   		 try {
			finalArray= algorithm.second("chebyshev", size, size, Bi, Bj, Ai, Aj, blocked);
			 path= finalArray[0];
			 arrayPath= path.toCharArray();
			for(int i=0; i<arrayPath.length;i+=2){
				show(randomlyGenMatrix,Character.getNumericValue(arrayPath[i]),
						Character.getNumericValue(arrayPath[i+1]));
			}
			show(randomlyGenMatrix,true,Ai,Aj,Bi,Bj);
			StdOut.println("Elapsed time = " + timerFlow1.elapsedTime());
		} catch (Exception e) {
			
		}
   	
   	break;
   	case 3:
   		
   		try {
			finalArray= algorithm.third("euclidean", size, size, Bi, Bj, Ai, Aj, blocked);
			 path= finalArray[0];
			 arrayPath= path.toCharArray();
			for(int i=0; i<arrayPath.length;i+=2){
				show(randomlyGenMatrix,Character.getNumericValue(arrayPath[i]),
						Character.getNumericValue(arrayPath[i+1]));
			}
			show(randomlyGenMatrix,true,Ai,Aj,Bi,Bj);
			StdOut.println("Elapsed time = " + timerFlow1.elapsedTime());
		} catch (Exception e) {
			
		}
   	
   	break;
   	
   	default:System.out.println("enter a valid input"); break;
   	
       }*/
       
   }

   public static void show(boolean[][]a,int x1,int  y1){
   	int N = a.length;
		StdDraw.setXscale(-1, N);
		;
		StdDraw.setYscale(-1, N);
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (a[i][j] == true){
					if((i==x1&&j==y1)){
						StdDraw.setPenColor(StdDraw.GREEN);
						StdDraw.filledSquare(j, N - i - 1, .5);
					}else{
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.square(j, N - i - 1, .5);
					}
					}else{
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.filledSquare(j, N - i - 1, .5);
				    	
				}
   			StdDraw.setPenColor(StdDraw.BLACK);
   			
}
   



}