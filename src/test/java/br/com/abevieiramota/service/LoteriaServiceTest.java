package br.com.abevieiramota.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@Ignore
@RunWith(JUnit4.class)
public class LoteriaServiceTest {

	@Test
	public void deveAtualizarOSistema() throws ClassNotFoundException, SQLException, ParseException, IOException {
		new LoteriaService().atualizar();
	}
}
