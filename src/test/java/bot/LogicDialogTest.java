package bot;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


class LogicDialogTest {
    @Test
    public void apiGenreDramaTest(){
        ApiFilm apiFilmMock = mock(ApiFilm.class);
        ConsoleInterface consoleInterfaceMock = mock(ConsoleInterface.class);
        when(consoleInterfaceMock.takeArg()).thenReturn("драма","хватит","стоп");
        JSONObject result = new JSONObject();
        JSONArray docs = new JSONArray();
        JSONObject film1 = new JSONObject().put("name","НАЗВАНИЕ").put("description","ОПИСАНИЕ");
        JSONObject rating = new JSONObject().put("kp","РЕЙТИНГ");
        film1.put("rating",rating);
        docs.put(film1);
        result.put("docs",docs);
        when(apiFilmMock.takeFilms(Type.valueOf("жанр"),"драма")).thenReturn(result);
        LogicDialog logicDialog= new LogicDialog(apiFilmMock,consoleInterfaceMock);
        //String input = "драма\nхватит\nстоп";
        //ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        //System.setIn(in);
        logicDialog.startDialog("жанр");
        verify(apiFilmMock).takeFilms(Type.valueOf("жанр"),"драма");
        //System.setIn(System.in);
    }
}