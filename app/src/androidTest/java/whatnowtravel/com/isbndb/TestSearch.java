package whatnowtravel.com.isbndb;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.Assert;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;



import com.robotium.solo.Solo;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import whatnowtravel.com.isbndb.activities.SearchActivity;

public class TestSearch extends ActivityInstrumentationTestCase2 {
    private Solo solo;



    public TestSearch() {
        super("com.Example.ApplicationTesting", SearchActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testClickOnSearch(){
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"data\":[{\"title\":\"Principles of solid mechanics\",\"subject_ids\":[\"mechanics_applied\"]," +
                "\"marc_enc_level\":\"\",\"author_data\":[{\"id\":\"richards_rowland\",\"name\":\"Richards, Rowland\"}]" +
                ",\"title_long\":\"\",\"dewey_normal\":\"620.105\",\"awards_text\":\"\",\"edition_info\":\"(alk. paper)\"," +
                "\"urls_text\":\"\",\"publisher_id\":\"crc_press\",\"title_latin\":\"Principles of solid mechanics\",\"isbn10\":\"084930315X\"," +
                "\"publisher_name\":\"CRC Press\",\"publisher_text\":\"Boca Raton, FL : CRC Press, 2000.\",\"language\":\"eng\",\"isbn13\":\"9780849303159\"," +
                "\"dewey_decimal\":\"620/.1/05\",\"notes\":\"Includes bibliographical references and index.\\n\\n1. Introduction -- 2. Strain and stress -- 3. Stress-strain relationships (rheology) -- 4. Strategies for elastic analysis and design -- 5. Linear free fields -- 6. Two-dimensional solutions for straight and circular beams -- 7. Ring, holes, and inverse problems -- 8. Wedges and half-space -- 9. Torsion -- 10. Concepts of plasticity -- 11. One-dimensional plasticity for design -- 12. Slip-line analysis.\",\"summary\":\"Evolving from more than 30 years of research and teaching experience, \\\"Principles of Solid Mechanics\\\" offers an in-depth treatment of the application of the full-range theory of deformable solids for analysis and design. Unlike other texts, it is not either a civil or mechanical engineering text, but both. It treats not only analysis but incorporates design along with experimental observation. Principles of Solid Mechanics serves as a core course textbook for advanced seniors and first-year graduate students. The author focuses on basic concepts and applications, simple yet unsolved problems, inverse strategies for optimum design, unanswered questions, and unresolved paradoxes to intrigue students and encourage further study. He includes plastic as well as elastic behavior in terms of a unified field theory and discusses the properties of field equations and requirements on boundary conditions crucial for understanding the limits of numerical modeling. Designed to help guide students with little experimental experience and no exposure to drawing and graphic analysis, the text presents carefully selected worked examples. The author makes liberal use of footnotes and includes over 150 figures and 200 problems. This, along with his approach, allows students to see the full range, non-linear response of structures.\",\"physical_description_text\":\"446 p. : ill. ; 24 cm.\",\"lcc_number\":\"TA350\",\"book_id\":\"principles_of_solid_mechanics\"}],\"index_searched\":\"isbn\"}"));
        try {
            server.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditText search = (EditText) getActivity().findViewById(R.id.search);
        Button ok = (Button) getActivity().findViewById(R.id.ok);
        ListView bookList = (ListView) getActivity().findViewById(R.id.listView1);
        solo.typeText(search, "anna");
        solo.clickOnButton("Search");
        ListAdapter ad = bookList.getAdapter();
        ArrayList<Book> books = new ArrayList<>();
        for(int i=0; i<ad.getCount(); i++){
            Book book = (Book) ad.getItem(i);
            books.add(book);
        }
        assertEquals(books.get(0).getName(), "Anna, Anna");
    }
}
