
public class TestSimplex {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] A = { { -1, 1, 0 }, 
				{ 1, 4, 0 }, 
				{ 2, 1, 0 }, 
				{ 3, -4, 0 }, 
				{ 0, 0, 1 }, 
		}; 
		double[] c = { 1, 1, 1 }; 
		double[] b = { 5, 45, 27, 24, 4 }; 
		Simplex2 lp = new Simplex2(A, b, c); 
		System.out.println(); 
		lp.show(); 
		lp.solve(); 
	}

}
