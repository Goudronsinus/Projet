public class Simplex { 
	private final static double INFINITY = Double.POSITIVE_INFINITY; 
	private double[][] a; // tableaux 
	private int M; // number of constraints 
	private int N; // number of original variables 

	private int[] basis; // basis[i] = basic variable corresponding to row i 
	// only needed to print out solution, not book 

	// sets up the simplex tableaux 
	public Simplex(double[][] A, double[] b, double[] c) { 
		M = b.length; 
		N = c.length; 
		a = new double[M+1][M+N+1]; 
		for (int i = 0; i < M; i++) 
			for (int j = 0; j < N; j++) 
				a[i][j] = A[i][j]; 
	
			for (int j = N; j < M + N; j++) 
				a[j-N][j] = 1.0; 
	
			for (int j = 0; j < N; j++) 
				a[M][j] = c[j]; 
	
		for (int i = 0; i < M; i++) 
				a[i][M+N] = b[i]; 

		basis = new int[M]; 
		for (int i = 0; i < M; i++) 
			basis[i] = M + i; 

	} 

	// return optimal objective value 
	public double value() { 
		return -a[M][M+N]; 
	} 

	// run simplex algorithm starting from initial BFS 
	public void solve() { 
		while (true) { 

			// find (first) objective function with positive coefficient 
			int q; 
			for (q = 0; q < M + N; q++) 
				if (a[M][q] > 0) 
					break; 
				if (q >= M + N) 
					break; // optimal 

				//// // find objective function with most positive coefficient	 
				//// q = 0; 
				//// for (int i = 1; i < M + N; i++) 
				//// if (a[M][i] > a[M][q]) q = i; 
				//// if (a[M][q] <= 0) break; // optimal 

				// find row p using min ratio rule 
			int p; 
			for (p = 0; p < M; p++) 
				if (a[p][q] > 0) break; 
			for (int i = p+1; i < M; i++) 
				if (a[i][q] > 0) 
					if (a[i][M+N] / a[i][q] < a[p][M+N] / a[p][q]) 
						p = i; 

			// pivot 
			if (p < M) pivot(p, q); 
			else { // unbounded 
				System.out.println("UNBOUNDED"); 
				return; 
			} 
			show(); 
		} 
	} 


	// pivot on entry (p, q) using Gauss-Jordan elimination 
	public void pivot(int p, int q) { 

		// everything but row p and column q 
		for (int i = 0; i <= M; i++) 
			for (int j = 0; j <= M + N; j++) 
				if (i != p && j != q) a[i][j] -= a[p][j] * a[i][q] / a[p][q]; 

		// zero out column q 
		for (int i = 0; i <= M; i++) 
			if (i != p) a[i][q] = 0.0; 

		// scale row p 
		for (int j = 0; j <= M + N; j++) 
			if (j != q) a[p][j] /= a[p][q]; 
				a[p][q] = 1.0; 

		// update basis 
		basis[p] = q; 
	} 

	// print tableaux 
	public void show() { 
		for (int i = 0; i <= M; i++) { 
			for (int j = 0; j <= M + N; j++) { 
				// System.out.printf("%7.2f ", a[i][j]); 
			} 
			System.out.println(); 
		} 
		System.out.println("value = " + value()); 
		for (int i = 0; i < M; i++) 
			if (basis[i] < M) System.out.println("x_" + basis[i] + " = " + a[i][M+N]); 
		System.out.println(); 
	} 

	public static void main(String[] args) { 
		double[][] A = { { -1, 1, 0 }, 
				{ 1, 4, 0 }, 
				{ 2, 1, 0 }, 
				{ 3, -4, 0 }, 
				{ 0, 0, 1 }, 
		}; 
		double[] c = { 1, 1, 1 }; 
		double[] b = { 5, 45, 27, 24, 4 }; 
		Simplex lp = new Simplex(A, b, c); 
		System.out.println(); 
		lp.show(); 
		lp.solve(); 
	} 


	// x0 = 12, x1 = 28, opt = 800 
	public static void test2() { 
		double[] c = { 13.0, 23.0 }; 
		double[] b = { 480.0, 160.0, 1190.0 }; 
		double[][] A = { { 5.0, 15.0 }, 
				{ 4.0, 4.0 }, 
				{ 35.0, 20.0 }, 
		}; 
		Simplex lp = new Simplex(A, b, c); 
		System.out.println(); 
		lp.show(); 
		lp.solve(); 
	} 

	// unbounded 
	public static void test3() { 
		double[] c = { 2.0, 3.0, -1.0, -12.0 }; 
		double[] b = { 3.0, 2.0 }; 
		double[][] A = { {-2.0, -9.0, 1.0, 9.0 }, 
				{ 1.0, 1.0, -1.0, -2.0 }, 
		}; 
		Simplex lp = new Simplex(A, b, c); 
		System.out.println(); 
		lp.show(); 
		lp.solve(); 
	} 

	// degenerate - cycles if you choose most positive objective function coefficient 
	public static void test4() { 
		double[] c = { 10.0, -57.0, -9.0, -24.0 }; 
		double[] b = { 0.0, 0.0, 1.0 }; 
		double[][] A = { { 0.5, -5.5, -2.5, 9.0 }, 
				{ 0.5, -1.5, -0.5, 1.0 }, 
				{ 1.0, 0.0, 0.0, 0.0 }, 
		}; 
		Simplex lp = new Simplex(A, b, c); 
		System.out.println(); 
		lp.show(); 
		lp.solve(); 
	} 

	public static void test10() { 
		double[] c = { -1,-1,4 }; 
		double[] b = { 9, 2, 4 }; 
		double[][] A = { { 1, 1,2 }, 
				{1, 1,-1 }, 
				{ -1, 1,1}, 
		}; 
		Simplex lp = new Simplex(A, b, c); 
		System.out.println(); 
		lp.show(); 
		lp.solve(); 
	} 
} 