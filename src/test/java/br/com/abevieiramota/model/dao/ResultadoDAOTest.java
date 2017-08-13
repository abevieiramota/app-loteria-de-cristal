package br.com.abevieiramota.model.dao;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.EnumSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.base.Joiner;

import br.com.abevieiramota.model.Turno;

@RunWith(JUnit4.class)
public class ResultadoDAOTest {

	@Test
	public void testInClause() {

		assertEquals("0,1", Joiner.on(",").join(Arrays.stream(Turno.values()).map(Enum::ordinal).toArray()));
	}
	
	@Test
	public void test() {
		System.out.println(EnumSet.allOf(Turno.class));
	}
}
