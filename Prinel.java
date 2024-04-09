import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Prinel {
	private int n;
	private int k;
	int[] target;
	int[] costs;
	int[] stepVec;

	//Citeste un int[] din fisier
	private void readArrayFromFile(BufferedReader myReader, int[] arr)
			throws IOException {
		String[] lineSplit = myReader.readLine().split(" ");
		for (int i = 0; i < this.getN(); i++) {
			arr[i] = Integer.parseInt(lineSplit[i]);
		}
	}

	//Citirea din fisier
	public void readFromFile(BufferedReader myReader) {
		try {
			String[] lineSplit = myReader.readLine().split(" ");
			this.n = Integer.parseInt(lineSplit[0]);
			this.k = Integer.parseInt(lineSplit[1]);


			this.target = new int[this.getN()];
			this.costs = new int[this.getN()];

			readArrayFromFile(myReader, this.target);
			readArrayFromFile(myReader, this.costs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//Verifia daca un numar este patrat perfect sau nu
	public static boolean checkPerfectSquare(int n) {
		if (Math.ceil((double) Math.sqrt(n)) == Math.floor((double) Math.sqrt(n))) {
			return true;
		}
		return false;
	}

	//Intoarce un Linked list sortat crescator
	private static LinkedList<Integer> getDivisors(int n) {
		LinkedList<Integer> vecFirstHalf = new LinkedList<>();
		LinkedList<Integer> vecSecondHalf = new LinkedList<>();
		double sqrtN = Math.sqrt(n);
		//Daca numarul nu este patrat perfect atunci merg pana la sqrt n
		//Daca numarul este patrat perfect merg pana la (sqrt n) - 1, si adaug in lista n / i
		boolean isPerfectSquare = checkPerfectSquare(n);
		if (isPerfectSquare) {
			sqrtN -= 1;
		}
		for (int i = 1; i <= sqrtN; i++) {
			if (n % i == 0) {
				vecFirstHalf.add(i);
				vecSecondHalf.addFirst((n / i));
			}
		}
		if (isPerfectSquare) {
			vecFirstHalf.add((int) sqrtN + 1);
		}
		vecFirstHalf.addAll(vecSecondHalf);
		return vecFirstHalf;
	}

	//Populeaza un ArrayList de n elemente cu intregi care reprezinta nr minim de pasi
	//prin care pot ajunge de la 1 adunand doar divizorii numarului curent
	public static void getBestSteps(ArrayList<Integer> vec, int n) {
		for (int i = 0; i < n + 1; i++) {
			vec.add(i, i - 1);
		}

		for (int i = 1; i < n + 1; i++) {
			LinkedList<Integer> allDivisors = getDivisors(i);
			for (Integer divisor : allDivisors) {
				//Deoarece lista este sortata crescator, daca i + divisor > n,
				//atunci si i + divisor + 1 va fi > n, deci dau break
				if (i + divisor > n) {
					break;
				}
				//La pozitia (i + divisor) salvez minimul dintre valoarea deja
				//stocata la pozitai (i + divisor) si valoarea de la pozitia (i) + 1
				vec.set(i + divisor, Math.min(vec.get(i) + 1,vec.get(i + divisor)));
			}
		}
	}

	//Creeaza un int[] care pe pozitia i pastreaza numarul de pasi minimi pana a ajunge la
	//numarul salvat in target[i].
	public void initStepVec() {
		//Creeaza un ArrayList de 10^5 elemente iar pe pozitia i se salveaza nr minim de pasi
		//pentru a ajunge la numarul i
		ArrayList<Integer> vec = new ArrayList<>(100000);
		getBestSteps(vec, 100000);
		this.stepVec = new int[this.getN()];
		for (int i = 0; i < this.getN(); i++) {
			this.stepVec[i] = vec.get(this.getTarget()[i]);
		}
	}


	//Problema rucsacului
	public int knapSack() {
		//dp[i] stochează profitul cu capacitatea knapSack „i”
		int[] res = new int[this.getK() + 1];

		//Initializeaza res cu 0
		Arrays.fill(res, 0);
		//Parcurg StepVec
		for (int i = 0; i < this.getN(); i++) {
			//Parcurg vectorul res de la sfarsit spre inceput
			for (int j = this.getK(); j >= this.getStepVec()[i]; j--) {
				//Max dintre res[j] (excluzand elementul i) si cost[i] +  res[j - stepVec[i]]
				res[j] = Math.max(res[j], this.getCosts()[i] + res[j - this.getStepVec()[i]]);
			}
		}
		return res[this.getK()];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("prinel.in"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("prinel.out"));

		Prinel prinel = new Prinel();
		prinel.readFromFile(br);

		prinel.initStepVec();
		bw.write("" + prinel.knapSack());

		br.close();
		bw.close();

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

	public int[] getTarget() {
		return target;
	}

	public void setTarget(int[] target) {
		this.target = target;
	}

	public int[] getCosts() {
		return costs;
	}

	public void setCosts(int[] costs) {
		this.costs = costs;
	}

	public int[] getStepVec() {
		return stepVec;
	}

	public void setStepVec(int[] stepVec) {
		this.stepVec = stepVec;
	}
}
