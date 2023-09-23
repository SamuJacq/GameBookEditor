package org.helmo.gbeditor.domains;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ISBNTests {

    @Test
    void testIsbn(){
        ISBN test = new ISBN("220001745X");

        assertEquals("220001745X", test.getNumero());

    }

}
