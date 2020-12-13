package dk.kea.taskz.TaskzTests;

import dk.kea.taskz.Models.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Tests {


    /**
     * Test skabeloner
     */

    @Test
    void Tests()
    {
                                                    // Metode i vores program som vi skal bruge til at hente data
        String hello = "Hello";                     // projectService.getHello();

        String assertEquals = "Hello";               // Den string vi FORVENTER der skal komme fra metoden ovenover - altid en magic string
        String assertNotEquals = "NotEqualHello";

        assertEquals(assertEquals,hello);
        assertNotEquals(assertNotEquals,hello);
        assertFalse(hello.equals("ikkeHello"));

        Project project = new Project();
        assertSame(project,project);

    }
}