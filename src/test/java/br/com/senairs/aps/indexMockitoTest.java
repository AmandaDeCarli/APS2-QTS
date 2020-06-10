package br.com.senairs.aps;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.senairs.aps2.CorreioService;

@RunWith(MockitoJUnitRunner.class)
public class indexMockitoTest {

		private CorreioService cep;
		
		@Before
		public void init() {
			CorreioService cep = Mockito.mock(CorreioService.class);
		}
}
