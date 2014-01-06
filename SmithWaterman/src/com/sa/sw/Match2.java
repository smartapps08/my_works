package com.sa.sw;

//Implementation of some algorithms for pairwise alignment from
//Durbin et al: Biological Sequence Analysis, CUP 1998, chapter 2.
//Peter Sestoft, sestoft@itu.dk 1999-09-25, 2003-04-20 version 1.4
//Reference:  http://www.itu.dk/people/sestoft/bsa.html

//License: Anybody can use this code for any purpose, including
//teaching, research, and commercial purposes, provided proper
//reference is made to its origin.  Neither the author nor the Royal
//Veterinary and Agricultural University, Copenhagen, Denmark, can
//take any responsibility for the consequences of using this code.

//Compile with:
//   javac Match2.java
//Run with:
//   java Match2 HEAGAWGHEE PAWHEAE

//Class hierarchies
//-----------------
//Align                   general pairwise alignment
//  AlignSimple          alignment with simple gap costs
//     NW                global alignment with simple gap costs
//     SW                local alignment with simple gap costs
//     RM                repeated matches with simple gap costs
//     OM                overlap matches with simple gap costs 
//  AlignAffine          alignment with affine gap costs (FSA model)
//     NWAffine          global alignment with affine gap costs
//  AlignSmart           alignment using smart linear-space algorithm
//     NWSmart           global alignment using linear space
//     SWSmart           local alignment using linear space
//  AlignSmartAffine     alignment w affine gap costs in linear space
//     SWSmartAffine     local alignment w affine gap costs in linear space
//Traceback               traceback pointers
//  Traceback2           traceback for simple gap costs
//  Traceback3           traceback for affine gap costs
//Substitution            substitution matrices with fast lookup
//  Blosum50             the BLOSUM50 substitution matrix
//Output                  general text output
//  SystemOut            output to the console (in the application)
//  TextAreaOut          output to a TextArea (in the applet)

//Notational conventions: 
//i in {0..n} indexes columns and sequence seq1
//j in {0..m} indexes rows    and sequence seq2
//k in {0..2} indexes states (in affine alignment)

//The class of substitution (scoring) matrices

abstract class Substitution {
	public int[][] score;

	void buildscore(String residues, int[][] residuescores) {
		// Allow lowercase and uppercase residues (ASCII code <= 127):
		score = new int[127][127];
		for (int i = 0; i < residues.length(); i++) {
			char res1 = residues.charAt(i);
			for (int j = 0; j <= i; j++) {
				char res2 = residues.charAt(j);
				score[res1][res2] = score[res2][res1] = score[res1][res2 + 32] = score[res2 + 32][res1] = score[res1 + 32][res2] = score[res2][res1 + 32] = score[res1 + 32][res2 + 32] = score[res2 + 32][res1 + 32] = residuescores[i][j];
			}
		}
	}

	abstract public String getResidues();
}

// The BLOSUM50 substitution matrix for amino acids (Durbin et al, p 16)

class Blosum50 extends Substitution {

	private String residues = "ARNDCQEGHILKMFPSTWYV";

	public String getResidues() {
		return residues;
	}

	private int[][] residuescores =
	/* A R N D C Q E G H I L K M F P S T W Y V */
	{ /* A */
			{ 5 },
			/* R */{ -2, 7 },
			/* N */{ -1, -1, 7 },
			/* D */{ -2, -2, 2, 8 },
			/* C */{ -1, -4, -2, -4, 13 },
			/* Q */{ -1, 1, 0, 0, -3, 7 },
			/* E */{ -1, 0, 0, 2, -3, 2, 6 },
			/* G */{ 0, -3, 0, -1, -3, -2, -3, 8 },
			/* H */{ -2, 0, 1, -1, -3, 1, 0, -2, 10 },
			/* I */{ -1, -4, -3, -4, -2, -3, -4, -4, -4, 5 },
			/* L */{ -2, -3, -4, -4, -2, -2, -3, -4, -3, 2, 5 },
			/* K */{ -1, 3, 0, -1, -3, 2, 1, -2, 0, -3, -3, 6 },
			/* M */{ -1, -2, -2, -4, -2, 0, -2, -3, -1, 2, 3, -2, 7 },
			/* F */{ -3, -3, -4, -5, -2, -4, -3, -4, -1, 0, 1, -4, 0, 8 },
			/* P */{ -1, -3, -2, -1, -4, -1, -1, -2, -2, -3, -4, -1, -3, -4, 10 },
			/* S */{ 1, -1, 1, 0, -1, 0, -1, 0, -1, -3, -3, 0, -2, -3, -1, 5 },
			/* T */{ 0, -1, 0, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1,
					2, 5 },
			/* W */{ -3, -3, -4, -5, -5, -1, -3, -3, -3, -3, -2, -3, -1, 1, -4,
					-4, -3, 15 },
			/* Y */{ -2, -1, -2, -3, -3, -1, -2, -3, 2, -1, -1, -2, 0, 4, -3,
					-2, -2, 2, 8 },
			/* V */{ 0, -3, -3, -4, -1, -3, -3, -4, -4, 4, 1, -3, 1, -1, -3,
					-2, 0, -3, -1, 5 }
	/* A R N D C Q E G H I L K M F P S T W Y V */
	};

	public Blosum50() {
		buildscore(residues, residuescores);
	}
}

// Pairwise sequence alignment

abstract class Align {
	Substitution sub; // substitution matrix
	int d; // gap cost
	String seq1, seq2; // the sequences
	int n, m; // their lengths
	Traceback B0; // the starting point of the traceback

	final static int NegInf = Integer.MIN_VALUE / 2; // negative infinity

	public Align(Substitution sub, int d, String seq1, String seq2) {
		this.sub = sub;
		this.seq1 = strip(seq1);
		this.seq2 = strip(seq2);
		this.d = d;
		this.n = this.seq1.length();
		this.m = this.seq2.length();
	}

	public String strip(String s) {
		boolean[] valid = new boolean[127];
		String residues = sub.getResidues();
		for (int i = 0; i < residues.length(); i++) {
			char c = residues.charAt(i);
			if (c < 96)
				valid[c] = valid[c + 32] = true;
			else
				valid[c - 32] = valid[c] = true;
		}
		StringBuffer res = new StringBuffer(s.length());
		for (int i = 0; i < s.length(); i++)
			if (valid[s.charAt(i)])
				res.append(s.charAt(i));
		return res.toString();
	}

	// Return two-element array containing an alignment with maximal score

	public String[] getMatch() {
		StringBuffer res1 = new StringBuffer();
		StringBuffer res2 = new StringBuffer();
		Traceback tb = B0;
		int i = tb.i, j = tb.j;
		while ((tb = next(tb)) != null) {
			if (i == tb.i)
				res1.append('-');
			else
				res1.append(seq1.charAt(i - 1));
			if (j == tb.j)
				res2.append('-');
			else
				res2.append(seq2.charAt(j - 1));
			i = tb.i;
			j = tb.j;
		}
		String[] res = { res1.reverse().toString(), res2.reverse().toString() };
		return res;
	}

	public String fmtscore(int val) {
		if (val < NegInf / 2)
			return "-Inf";
		else
			return Integer.toString(val);
	}

	// Print the score, the F matrix, and the alignment
	public void domatch(Output out, String msg, boolean udskrivF) {
		out.println(msg + ":");
		out.println("Score = " + getScore());
		if (udskrivF) {
			out.println("The F matrix:");
			printf(out);
		}
		out.println("An optimal alignment:");
		String[] match = getMatch();
		out.println(match[0]);
		out.println(match[1]);
		out.println();
	}

	public void domatch(Output out, String msg) {
		domatch(out, msg, true);
	}

	// Get the next state in the traceback
	public Traceback next(Traceback tb) {
		return tb;
	} // dummy implementation for the `smart' algs.

	// Return the score of the best alignment
	public abstract int getScore();

	// Print the matrix (matrices) used to compute the alignment
	public abstract void printf(Output out);

	// Auxiliary functions
	static int max(int x1, int x2) {
		return (x1 > x2 ? x1 : x2);
	}

	static int max(int x1, int x2, int x3) {
		return max(x1, max(x2, x3));
	}

	static int max(int x1, int x2, int x3, int x4) {
		return max(max(x1, x2), max(x3, x4));
	}

	static String padLeft(String s, int width) {
		int filler = width - s.length();
		if (filler > 0) { // and therefore width > 0
			StringBuffer res = new StringBuffer(width);
			for (int i = 0; i < filler; i++)
				res.append(' ');
			return res.append(s).toString();
		} else
			return s;
	}
}

// Alignment with simple gap costs

abstract class AlignSimple extends Align {
	int[][] F; // the matrix used to compute the alignment
	Traceback2[][] B; // the traceback matrix

	public AlignSimple(Substitution sub, int d, String seq1, String seq2) {
		super(sub, d, seq1, seq2);
		F = new int[n + 1][m + 1];
		B = new Traceback2[n + 1][m + 1];
	}

	public Traceback next(Traceback tb) {
		Traceback2 tb2 = (Traceback2) tb;
		return B[tb2.i][tb2.j];
	}

	public int getScore() {
		return F[B0.i][B0.j];
	}

	public void printf(Output out) {
		for (int j = 0; j <= m; j++) {
			for (int i = 0; i < F.length; i++)
				out.print(padLeft(fmtscore(F[i][j]), 5));
			out.println();
		}
	}
}

// Traceback objects

abstract class Traceback {
	int i, j; // absolute coordinates
}

// Traceback2 objects for simple gap costs

class Traceback2 extends Traceback {
	public Traceback2(int i, int j) {
		this.i = i;
		this.j = j;
	}
}

// Auxiliary classes for output

abstract class Output {
	public abstract void print(String s);

	public abstract void println(String s);

	public abstract void println();
}

class SystemOut extends Output {
	public void print(String s) {
		System.out.print(s);
	}

	public void println(String s) {
		System.out.println(s);
	}

	public void println() {
		System.out.println();
	}
}

// Global alignment with the Needleman-Wunsch algorithm (simple gap costs)

class NW extends AlignSimple {

	public NW(Substitution sub, int d, String sq1, String sq2) {
		super(sub, d, sq1, sq2);
		int n = this.n, m = this.m;
		int[][] score = sub.score;
		for (int i = 1; i <= n; i++) {
			F[i][0] = -d * i;
			B[i][0] = new Traceback2(i - 1, 0);
		}
		for (int j = 1; j <= m; j++) {
			F[0][j] = -d * j;
			B[0][j] = new Traceback2(0, j - 1);
		}
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= m; j++) {
				int s = score[seq1.charAt(i - 1)][seq2.charAt(j - 1)];
				int val = max(F[i - 1][j - 1] + s, F[i - 1][j] - d, F[i][j - 1]
						- d);
				F[i][j] = val;
				if (val == F[i - 1][j - 1] + s)
					B[i][j] = new Traceback2(i - 1, j - 1);
				else if (val == F[i - 1][j] - d)
					B[i][j] = new Traceback2(i - 1, j);
				else if (val == F[i][j - 1] - d)
					B[i][j] = new Traceback2(i, j - 1);
				else
					throw new Error("NW 1");
			}
		B0 = new Traceback2(n, m);
	}
}

// Local alignment with the Smith-Waterman algorithm (simple gap costs)

class SW extends AlignSimple {
	public SW(Substitution sub, int d, String sq1, String sq2) {
		super(sub, d, sq1, sq2);
		int n = this.n, m = this.m;
		int[][] score = sub.score;
		int maxi = n, maxj = m;
		int maxval = NegInf;
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= m; j++) {
				int s = score[seq1.charAt(i - 1)][seq2.charAt(j - 1)];
				int val = max(0, F[i - 1][j - 1] + s, F[i - 1][j] - d,
						F[i][j - 1] - d);
				F[i][j] = val;
				if (val == 0)
					B[i][j] = null;
				else if (val == F[i - 1][j - 1] + s)
					B[i][j] = new Traceback2(i - 1, j - 1);
				else if (val == F[i - 1][j] - d)
					B[i][j] = new Traceback2(i - 1, j);
				else if (val == F[i][j - 1] - d)
					B[i][j] = new Traceback2(i, j - 1);
				else
					throw new Error("SW 1");
				if (val > maxval) {
					maxval = val;
					maxi = i;
					maxj = j;
				}
			}
		B0 = new Traceback2(maxi, maxj);
	}
}

// Repeated matches (simple gap costs)

class RM extends AlignSimple {
	private int T;

	public RM(Substitution sub, int d, int T, String sq1, String sq2) {
		super(sub, d, sq1, sq2);
		this.T = T;
		int n = this.n, m = this.m;
		int[][] score = sub.score;
		// F[0][0..m] = 0 by construction
		for (int i = 1; i <= n; i++) {
			int maxj = maxj(i - 1);
			F[i][0] = maxjval(i - 1, maxj);
			B[i][0] = new Traceback2(i - 1, maxj);
			for (int j = 1; j <= m; j++) {
				int s = score[seq1.charAt(i - 1)][seq2.charAt(j - 1)];
				int val = max(F[i][0], F[i - 1][j - 1] + s, F[i - 1][j] - d,
						F[i][j - 1] - d);
				F[i][j] = val;
				if (val == F[i][0])
					B[i][j] = new Traceback2(i, 0);
				else if (val == F[i - 1][j - 1] + s)
					B[i][j] = new Traceback2(i - 1, j - 1);
				else if (val == F[i - 1][j] - d)
					B[i][j] = new Traceback2(i - 1, j);
				else if (val == F[i][j - 1] - d)
					B[i][j] = new Traceback2(i, j - 1);
				else
					throw new Error("RM 1");
			}
		}
		B0 = new Traceback2(n, maxj(n));
	}

	public String[] getMatch() {
		StringBuffer res1 = new StringBuffer();
		StringBuffer res2 = new StringBuffer();
		Traceback tb = B0;
		int i = tb.i, j = tb.j;
		while ((tb = next(tb)) != null) {
			if (i != tb.i) { // Never make a gap in seq1
				res1.append(seq1.charAt(i - 1));
				if (j == 0)
					res2.append('.');
				else if (j == tb.j)
					res2.append('-');
				else
					res2.append(seq2.charAt(j - 1));
			}
			i = tb.i;
			j = tb.j;
		}
		String[] res = { res1.reverse().toString(), res2.reverse().toString() };
		return res;
	}

	private int maxj(int i) {
		int maxj = 0, val = F[i][maxj] + T;
		for (int j = 1; j <= m; j++)
			if (val < F[i][j]) {
				maxj = j;
				val = F[i][j];
			}
		return maxj;
	}

	private int maxjval(int i, int maxj) {
		if (maxj == 0)
			return F[i][0];
		else
			return F[i][maxj] - T;
	}
}

// Overlap matching (simple gap costs)

class OM extends AlignSimple {

	public OM(Substitution sub, int d, String sq1, String sq2) {
		super(sub, d, sq1, sq2);
		int n = this.n, m = this.m;
		int[][] score = sub.score;
		// F[0][0..m] = F[0..n][0] = 0 by construction
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= m; j++) {
				int s = score[seq1.charAt(i - 1)][seq2.charAt(j - 1)];
				int val = max(F[i - 1][j - 1] + s, F[i - 1][j] - d, F[i][j - 1]
						- d);
				F[i][j] = val;
				if (val == F[i - 1][j - 1] + s)
					B[i][j] = new Traceback2(i - 1, j - 1);
				else if (val == F[i - 1][j] - d)
					B[i][j] = new Traceback2(i - 1, j);
				else if (val == F[i][j - 1] - d)
					B[i][j] = new Traceback2(i, j - 1);
				else
					throw new Error("RM 1");
			}
		// Find maximal score on right-hand and bottom borders
		int maxi = -1, maxj = -1;
		int maxval = NegInf;
		for (int i = 0; i <= n; i++)
			if (maxval < F[i][m]) {
				maxi = i;
				maxval = F[i][m];
			}
		for (int j = 0; j <= m; j++)
			if (maxval < F[n][j]) {
				maxj = j;
				maxval = F[n][j];
			}
		if (maxj != -1) // the maximum score was F[n][maxj]
			B0 = new Traceback2(n, maxj);
		else
			// the maximum score was F[maxi][m]
			B0 = new Traceback2(maxi, m);
	}
}

// Traceback3 objects for affine gap costs

class Traceback3 extends Traceback {
	int k;

	public Traceback3(int k, int i, int j) {
		this.k = k;
		this.i = i;
		this.j = j;
	}
}

// Alignment with affine gap costs

abstract class AlignAffine extends Align {
	int e; // gap extension cost
	int[][][] F; // the matrices used to compute the alignment
	Traceback3[][][] B; // the traceback matrix

	public AlignAffine(Substitution sub, int d, int e, String sq1, String sq2) {
		super(sub, d, sq1, sq2);
		this.e = e;
		F = new int[3][n + 1][m + 1];
		B = new Traceback3[3][n + 1][m + 1];
	}

	public Traceback next(Traceback tb) {
		Traceback3 tb3 = (Traceback3) tb;
		return B[tb3.k][tb3.i][tb3.j];
	}

	public int getScore() {
		return F[((Traceback3) B0).k][B0.i][B0.j];
	}

	public void printf(Output out) {
		for (int k = 0; k < 3; k++) {
			out.println("F[" + k + "]:");
			for (int j = 0; j <= m; j++) {
				for (int i = 0; i < F[k].length; i++)
					out.print(padLeft(fmtscore(F[k][i][j]), 5));
				out.println();
			}
		}
	}
}

// Global alignment using the Needleman-Wunsch algorithm (affine gap costs)

class NWAffine extends AlignAffine {

	public NWAffine(Substitution sub, int d, int e, String sq1, String sq2) {
		super(sub, d, e, sq1, sq2);
		int n = this.n, m = this.m;
		int[][] score = sub.score;
		int[][] M = F[0], Ix = F[1], Iy = F[2];
		for (int i = 1; i <= n; i++) {
			Ix[i][0] = -d - e * (i - 1);
			B[1][i][0] = new Traceback3(1, i - 1, 0);
		}
		for (int i = 1; i <= n; i++)
			Iy[i][0] = M[i][0] = NegInf;
		for (int j = 1; j <= m; j++) {
			Iy[0][j] = -d - e * (j - 1);
			B[2][0][j] = new Traceback3(2, 0, j - 1);
		}
		for (int j = 1; j <= m; j++)
			Ix[0][j] = M[0][j] = NegInf;
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= m; j++) {
				int val;
				int s = score[seq1.charAt(i - 1)][seq2.charAt(j - 1)];
				val = M[i][j] = max(M[i - 1][j - 1] + s, Ix[i - 1][j - 1] + s,
						Iy[i - 1][j - 1] + s);
				if (val == M[i - 1][j - 1] + s)
					B[0][i][j] = new Traceback3(0, i - 1, j - 1);
				else if (val == Ix[i - 1][j - 1] + s)
					B[0][i][j] = new Traceback3(1, i - 1, j - 1);
				else if (val == Iy[i - 1][j - 1] + s)
					B[0][i][j] = new Traceback3(2, i - 1, j - 1);
				else
					throw new Error("NWAffine 1");
				val = Ix[i][j] = max(M[i - 1][j] - d, Ix[i - 1][j] - e,
						Iy[i - 1][j] - d);
				if (val == M[i - 1][j] - d)
					B[1][i][j] = new Traceback3(0, i - 1, j);
				else if (val == Ix[i - 1][j] - e)
					B[1][i][j] = new Traceback3(1, i - 1, j);
				else if (val == Iy[i - 1][j] - d)
					B[1][i][j] = new Traceback3(2, i - 1, j);
				else
					throw new Error("NWAffine 2");
				val = Iy[i][j] = max(M[i][j - 1] - d, Iy[i][j - 1] - e,
						Ix[i][j - 1] - d);
				if (val == M[i][j - 1] - d)
					B[2][i][j] = new Traceback3(0, i, j - 1);
				else if (val == Iy[i][j - 1] - e)
					B[2][i][j] = new Traceback3(2, i, j - 1);
				else if (val == Ix[i][j - 1] - d)
					B[2][i][j] = new Traceback3(1, i, j - 1);
				else
					throw new Error("NWAffine 3");
			}
		// Find maximal score
		int maxk = 0;
		int maxval = F[0][n][m];
		for (int k = 1; k < 3; k++)
			if (maxval < F[k][n][m]) {
				maxval = F[k][n][m];
				maxk = k;
			}
		B0 = new Traceback3(maxk, n, m);
	}
}

// Alignment with simple gap costs; smart linear-space algorithm

abstract class AlignSmart extends Align {
	int[][] F;

	public AlignSmart(Substitution sub, int d, String sq1, String sq2) {
		super(sub, d, sq1, sq2);
		F = new int[2][m + 1];
	}

	public void printf(Output out) {
		for (int j = 0; j <= m; j++) {
			for (int i = 0; i < F.length; i++)
				out.print(padLeft(fmtscore(F[i][j]), 5));
			out.println();
		}
	}

	static void swap01(Object[] A) {
		Object tmp = A[1];
		A[1] = A[0];
		A[0] = tmp;
	}
}

// Global alignment (simple gap costs, smart linear-space algorithm)

class NWSmart extends AlignSmart {
	int u; // Halfway through seq1
	int[][] c; // Best alignment from (0,0) to (i,j) passes through (u, c[i][j])

	public NWSmart(Substitution sub, int d, String sq1, String sq2) {
		super(sub, d, sq1, sq2);
		int n = this.n, m = this.m;
		u = n / 2;
		c = new int[2][m + 1];
		int[][] score = sub.score;
		for (int j = 0; j <= m; j++)
			F[1][j] = -d * j;
		for (int i = 1; i <= n; i++) {
			swap01(F);
			swap01(c);
			// F[1] represents (new) column i and F[0] represents (old) column
			// i-1
			F[1][0] = -d * i;
			for (int j = 1; j <= m; j++) {
				int s = score[seq1.charAt(i - 1)][seq2.charAt(j - 1)];
				int val = max(F[0][j - 1] + s, F[0][j] - d, F[1][j - 1] - d);
				F[1][j] = val;
				if (i == u)
					c[1][j] = j;
				else if (val == F[0][j - 1] + s)
					c[1][j] = c[0][j - 1];
				else if (val == F[0][j] - d)
					c[1][j] = c[0][j];
				else if (val == F[1][j - 1] - d)
					c[1][j] = c[1][j - 1];
				else
					throw new Error("NWSmart 1");
			}
		}
	}

	public int getV() {
		return c[1][m];
	}

	public String[] getMatch() {
		int v = getV();
		if (n > 1 && m > 1) {
			NWSmart al1, al2;
			al1 = new NWSmart(sub, d, seq1.substring(0, u),
					seq2.substring(0, v));
			al2 = new NWSmart(sub, d, seq1.substring(u), seq2.substring(v));
			String[] match1 = al1.getMatch();
			String[] match2 = al2.getMatch();
			String[] match = { match1[0] + match2[0], match1[1] + match2[1] };
			return match;
		} else {
			NW al = new NW(sub, d, seq1, seq2);
			return al.getMatch();
		}
	}

	public int getScore() {
		return F[1][m];
	}
}

// Local alignment with the Smith-Waterman algorithm (simple gap
// costs, smart linear space algorithm)

class SWSmart extends AlignSmart {
	Traceback2[][] start; // Best alignment ending at (i,j) begins at
							// start[i][j]
	int maxval; // Score of best alignment
	int start1, start2; // Best alignment begins at (start1, start2)
	int end1, end2; // Best alignment ends at (end1, end2)

	public SWSmart(Substitution sub, int d, String sq1, String sq2) {
		super(sub, d, sq1, sq2);
		int n = this.n, m = this.m;
		int[][] score = sub.score;
		start = new Traceback2[2][m + 1];
		maxval = NegInf;
		// Initialize first column (i=0):
		for (int j = 0; j <= m; j++)
			start[1][j] = new Traceback2(0, j);
		for (int i = 1; i <= n; i++) {
			swap01(F);
			swap01(start);
			// F[1] represents (new) column i and F[0] represents (old) column
			// i-1
			// Initialize first row (j=0):
			start[1][0] = new Traceback2(i, 0);
			for (int j = 1; j <= m; j++) {
				int s = score[seq1.charAt(i - 1)][seq2.charAt(j - 1)];
				int val = max(0, F[0][j - 1] + s, F[0][j] - d, F[1][j - 1] - d);
				F[1][j] = val;
				if (val == 0) // Best alignment starts (and ends) here
					start[1][j] = new Traceback2(i, j);
				else if (val == F[0][j - 1] + s)
					start[1][j] = start[0][j - 1];
				else if (val == F[0][j] - d)
					start[1][j] = start[0][j];
				else if (val == F[1][j - 1] - d)
					start[1][j] = start[1][j - 1];
				else
					throw new Error("SWSmart 1");
				if (val > maxval) {
					maxval = val;
					Traceback2 sij = start[1][j];
					start1 = sij.i;
					start2 = sij.j;
					end1 = i;
					end2 = j;
				}
			}
		}
	}

	public int getScore() {
		return maxval;
	}

	public String[] getMatch() {
		String subseq1 = seq1.substring(start1, end1);
		String subseq2 = seq2.substring(start2, end2);
		// The optimal local alignment between seq1 and seq2 is the
		// optimal global alignment between subseq1 and subseq2:
		return (new NWSmart(sub, d, subseq1, subseq2)).getMatch();
	}
}

// Alignment with affine gap costs; smart linear-space algorithm

abstract class AlignSmartAffine extends Align {
	int e; // gap extension cost
	int[][][] F; // the matrices used to compute the alignment

	public AlignSmartAffine(Substitution sub, int d, int e, String sq1,
			String sq2) {
		super(sub, d, sq1, sq2);
		this.e = e;
		F = new int[3][2][m + 1];
	}

	public void printf(Output out) {
		for (int k = 0; k < 3; k++) {
			out.println("F[" + k + "]:");
			for (int j = 0; j <= m; j++) {
				for (int i = 0; i < F[k].length; i++)
					out.print(padLeft(fmtscore(F[k][i][j]), 5));
				out.println();
			}
		}
	}

	static void swap01(Object[] A) {
		Object tmp = A[1];
		A[1] = A[0];
		A[0] = tmp;
	}
}

// Local alignment with the Smith-Waterman algorithm (affine gap
// costs, smart linear space algorithm)

class SWSmartAffine extends AlignSmartAffine {
	Traceback2[][] start; // Best alignment ending at (i,j) begins at
							// start[i][j]
	int maxval; // Score of best alignment
	int start1, start2; // Best alignment begins at (start1, start2)
	int end1, end2; // Best alignment ends at (end1, end2)

	public SWSmartAffine(Substitution sub, int d, int e, String sq1, String sq2) {
		super(sub, d, e, sq1, sq2);
		int n = this.n, m = this.m;
		int[][] score = sub.score;
		int[][] M = F[0], Ix = F[1], Iy = F[2];
		start = new Traceback2[2][m + 1];
		maxval = NegInf;
		// Initialize first column (i=0); score is zero:
		for (int j = 0; j <= m; j++)
			start[1][j] = new Traceback2(0, j);
		for (int i = 1; i <= n; i++) {
			swap01(M);
			swap01(Ix);
			swap01(Iy);
			swap01(start);
			// F[k][1] represents (new) col i and F[k][0] represents (old) col
			// i-1
			// Initialize first row (j=0):
			start[1][0] = new Traceback2(i, 0);
			for (int j = 1; j <= m; j++) {
				int s = score[seq1.charAt(i - 1)][seq2.charAt(j - 1)];
				int val, valm, valix, valiy;
				valm = M[1][j] = max(0, M[0][j - 1] + s, Ix[0][j - 1] + s,
						Iy[0][j - 1] + s);
				valix = Ix[1][j] = max(M[0][j] - d, Ix[0][j] - e);
				valiy = Iy[1][j] = max(M[1][j - 1] - d, Iy[1][j - 1] - e);

				val = max(valm, valix, valiy);
				if (val == 0)
					start[1][j] = new Traceback2(i, j);
				else if (val == valm)
					start[1][j] = start[0][j - 1];
				else if (val == valix)
					start[1][j] = start[0][j];
				else if (val == valiy)
					start[1][j] = start[1][j - 1];
				else
					throw new Error("SWSmartAffine 1");
				if (val > maxval) {
					maxval = val;
					Traceback2 sij = start[1][j];
					start1 = sij.i;
					start2 = sij.j;
					end1 = i;
					end2 = j;
				}
			}
		}
	}

	public int getScore() {
		return maxval;
	}

	public String[] getMatch() {
		String subseq1 = seq1.substring(start1, end1);
		String subseq2 = seq2.substring(start2, end2);
		// The optimal local alignment between seq1 and seq2 is the
		// optimal global alignment between subseq1 and subseq2:
		return (new NWAffine(sub, d, e, subseq1, subseq2)).getMatch();
	}
}

// Test all seven alignment algorithms

public class Match2 {
	public static void main(String[] args) {
		Output out = new SystemOut();
		String seq1 = "gattaca";
		String seq2 = "ggaattttaaccaa";

		Substitution sub = new Blosum50();
		(new NW(sub, 8, seq1, seq2)).domatch(out, "GLOBAL ALIGNMENT");
		(new SW(sub, 8, seq1, seq2)).domatch(out, "LOCAL ALIGNMENT");
		(new RM(sub, 8, 20, seq1, seq2)).domatch(out, "REPEATED MATCHES");
		(new OM(sub, 8, seq1, seq2)).domatch(out, "OVERLAP MATCH");
		(new NWAffine(sub, 12, 2, seq1, seq2)).domatch(out, "AFFINE GLOBAL");
		(new NWSmart(sub, 8, seq1, seq2)).domatch(out, "SMART GLOBAL");
		(new SWSmart(sub, 8, seq1, seq2)).domatch(out, "SMART LOCAL");
		(new SWSmartAffine(sub, 12, 2, seq1, seq2)).domatch(out,
				"SMART AFFINE LOCAL");
	}
}