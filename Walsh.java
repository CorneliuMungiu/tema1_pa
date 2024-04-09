import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Walsh {
	private int n;
	private int k;
	private Pairs[] pairs;

	private class Pairs {
		private int x;
		private int y;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public Pairs(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	//Read from file
	public void readFromFile(BufferedReader myReader) {
		try {
			String[] lineSplit = myReader.readLine().split(" ", 2);
			this.n = Integer.parseInt(lineSplit[0]);
			this.k = Integer.parseInt(lineSplit[1]);
			this.pairs = new Pairs[this.k];

			for (int i = 0; i < k; i++) {
				lineSplit = myReader.readLine().split(" ", 2);
				this.pairs[i] = new Pairs(Integer.parseInt(lineSplit[0]),
											Integer.parseInt(lineSplit[1]));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//x = !x
	public static int denied(int Wn) {
		if (Wn == 0) {
			return 1;
		}
		return 0;
	}


	public int getResult(int n, int x, int y, int Wn) {
		//Conditie de iesire din recursie
		if (n == 1) {
			return Wn;
		}

		int halfN = n / 2;

		if (x <= halfN) {
			//Cadranul 1
			if (y <= halfN) {
				return getResult(halfN, x, y, Wn);
			}
			//Cadranul 2
			return getResult(halfN, x, y - halfN, Wn);
		} else {
			//Cadranul 3
			if (y <= halfN) {
				return getResult(halfN, x - halfN, y, Wn);
			}
			//Cadranul 4
			return getResult(halfN, x - halfN, y - halfN, denied(Wn));
		}
	}

	public static void main(String[] args) throws IOException {
		Walsh table = new Walsh();
		BufferedReader br = new BufferedReader(new FileReader("walsh.in"));
		table.readFromFile(br);
		BufferedWriter bw = new BufferedWriter(new FileWriter("walsh.out"));

		for (int i = 0; i < table.getK(); i++) {
			bw.write("" + table.getResult(table.getN(), table.getPairs()[i].getX(),
					table.getPairs()[i].getY(), 0) + "\n");
		}
		bw.close();
		br.close();
	}

	public Walsh() {
	}

	public Walsh(int n, int k, Pairs[] pairs) {
		this.n = n;
		this.k = k;
		this.pairs = pairs;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public Pairs[] getPairs() {
		return pairs;
	}

	public void setPairs(Pairs[] pairs) {
		this.pairs = pairs;
	}
}
