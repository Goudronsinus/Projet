package landscapegenerator;

public class SimplexNoiseLandscapeGenerator {

	private final static double INFINITY = Double.POSITIVE_INFINITY; 
	private static double[][] a; // tableaux 
	private static int M; // number of constraints 
	private static int N; // number of original variables 

	private static int[] basis; // basis[i] = basic variable corresponding to row i 
	// only needed to print out solution, not book 

	public static double[][] generateSimplexNoiseLandscape( int dxView, int dyView, double scaling, double landscapeAltitudeRatio, int perlinLayerCount )
	{
		double[][] landscape = new double[dxView][dyView];
		
		for (int x = 0 ; x < dxView ; x++)
			for (int y = 0 ; y < dyView ; y++)
				landscape[x][y] = Math.random();
		
		double[] b = new double[dxView];
		double[] c = new double[dyView];
		
		for (int i = 0; i < dxView ; i++) {
			b[i] = 1;
		}
		for (int i = 0; i < dyView ; i++) {
			c[i] = Math.random()*landscapeAltitudeRatio;
		}
		
		for (int i = 0 ; i < perlinLayerCount ; i++) {
			Simplex2(landscape, b, c);
		///this.
			solve();
		}
		landscape = LandscapeToolbox.scaleAndCenter(landscape, scaling, landscapeAltitudeRatio);
    	landscape = LandscapeToolbox.smoothLandscape(landscape);
		return landscape;
		
	}
	// sets up the simplex tableaux 
	public static void Simplex2(double[][] A, double[] b, double[] c) { 
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
	public static void solve() { 
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
			//show(); 
		} 
	} 
	
	// pivot on entry (p, q) using Gauss-Jordan elimination 
		public static void pivot(int p, int q) { 

			// everything but row p and column q 
			for (int i = 0; i <= M; i++) 
				for (int j = 0; j <= M + N; j++) 
					if (i != p && j != q) 
						a[i][j] -= a[p][j] * a[i][q] / a[p][q]; 

			// zero out column q 
			for (int i = 0; i <= M; i++) 
				if (i != p) a[i][q] = 0.0; 

			// scale row p 
			for (int j = 0; j <= M + N; j++) 
				if (j != q) 
					a[p][j] /= a[p][q]; 
					a[p][q] = 1.0; 

			// update basis 
			basis[p] = q; 
		} 
}
