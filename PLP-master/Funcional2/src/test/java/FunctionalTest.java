package test.java;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.io.StringReader;

import lf2.plp.functional2.parser.Func2Parser;
import lf2.plp.functional2.parser.ParseException;
import lf2.plp.functional2.Programa;

public class FunctionalTest {

    @Test
    public void testDebugGradiente() throws Exception {
        String codigo = "let var x = 1 in let var y = 2 in " +
                        "let fun f x y = 2 * x * x + 3 * x * y + y * y in grad f by [x, y]";

        System.out.println("=== DEBUG ===");
        System.out.println("Código: " + codigo);

        Func2Parser parser = new Func2Parser(new StringReader(codigo));
        Programa prog = parser.Input();

        // Adicione prints no ExpGradiente.avaliar() também!
        Object resultado = prog.executar();

        System.out.println("Resultado: " + resultado);
        System.out.println("=============");
    }
}
