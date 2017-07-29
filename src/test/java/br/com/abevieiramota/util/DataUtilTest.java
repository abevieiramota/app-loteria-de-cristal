package br.com.abevieiramota.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DataUtilTest {

	private String dataInicio1 = "20/08/2000";
	private String dataFim1 = "23/08/2000";
	
	private List<String> intervalo1 = Arrays.asList("20/08/2000", "21/08/2000", "22/08/2000", "23/08/2000");
	private List<String> intervalo2 = Arrays.asList("30/07/2013", "31/07/2013", "01/08/2013", "02/08/2013");

	@Test
	public void deveCompararCorretamenteDuasDatasComoStringMenor() throws ParseException {
		
		assertTrue(DataUtil.compare(dataInicio1, dataFim1) < 0);
	}
	
	@Test
	public void deveCompararCorretamenteDuasDatasComoStringMaior() throws ParseException {
		
		assertTrue(DataUtil.compare(dataFim1, dataInicio1) > 0);
	}
	
	@Test
	public void deveCompararCorretamenteDuasDatasComoStringIgual() throws ParseException {
		
		assertTrue(DataUtil.compare(dataInicio1, dataInicio1) == 0);
	}
	
	@Test
	public void deveRetornarRangeCorreto() throws ParseException {
		List<String> range = DataUtil.range(dataInicio1, dataFim1);
		
		assertEquals(intervalo1, range);
	}
	
	@Test
	public void deveRetornarRangeCorreto2() throws ParseException {
		List<String> range = DataUtil.range(intervalo2.get(0), intervalo2.get(intervalo2.size() - 1));
		
		assertEquals(intervalo2, range);
	}
	
}
