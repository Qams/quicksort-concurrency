import static org.junit.Assert.*;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import org.junit.Before;
import org.junit.Test;

public class QuicksortTest {
	
	private int countElements = 1000000;
	private int countThreads = 8;
	private int maxValue = 500000;
	Integer[][] t = new Integer[countThreads][countElements];
	
	@Before
	public void initialize()
	{
		Random rand = new Random();
		for(int i=0;i<t[0].length;i++)
		{
			int x = rand.nextInt(maxValue);
			for(int j=0;j<8;j++){
				t[j][i] = x;
			}
		}
	}

	@Test
	public void quicksortIntegerTest() {
		
		for(int i=1;i<=countThreads;i++)
		{
			Quicksort<Integer> quick = new Quicksort<>(t[i-1],0,countElements-1);
			ForkJoinPool fj = new ForkJoinPool(i);
			long start = System.currentTimeMillis();
			fj.invoke(quick);
			long stop = System.currentTimeMillis();
			for(int j=0;j<countElements-1;j++)
				assertTrue(t[i-1][j].compareTo(t[i-1][j+1])<=0);
			System.out.println(i + " ,TIME:" + (stop-start) );
		}
		
	}

}
