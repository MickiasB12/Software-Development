package edu.lehigh.cse216.brp221.backend;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import edu.lehigh.cse216.brp221.backend.handler.GetSelectAllHandlers;
import edu.lehigh.cse216.brp221.backend.handler.PostInsertOneHandlers;
import edu.lehigh.cse216.brp221.backend.handler.PutLikeOneHandlers;
import edu.lehigh.cse216.brp221.backend.helper.Answer;
import edu.lehigh.cse216.brp221.backend.helper.Model;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.easymock.EasyMock.*;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App
 */
public class AppTest {

    public final Gson gson = new Gson();

    @Test
    public void testLike() {

        Model model = EasyMock.createMock(Model.class);
        expect(model.likeOne(1)).andReturn(true);
        replay(model);

        PutLikeOneHandlers likeHandler = new PutLikeOneHandlers(model);
        Map<String, String> urlParams = Maps.newHashMap();
        urlParams.put(":id", "1");
        assertEquals(new Answer(200, gson.toJson(new StructuredResponse("ok", null, null))),
                likeHandler.process(null, urlParams, false));

        verify(model);

    }
    
}
