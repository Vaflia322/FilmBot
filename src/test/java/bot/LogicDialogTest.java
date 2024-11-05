package bot;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;


class LogicDialogTest {
    @Test
    public void apiGenreDramaTest(){
        ApiFilm apiFilmMock = mock(ApiFilm.class);
        Dialog dialogMock = mock(Dialog.class);
        User user = new User(1);
        UserMessage[] userMessages = {new UserMessage(user,"подскажи фильм"),new UserMessage(user,"жанр"),new UserMessage(user,"драма"),new UserMessage(user,"хватит"), new UserMessage(user,"стоп")};
        when(dialogMock.takeArg(user)).thenReturn(userMessages[0],userMessages[1],userMessages[2],userMessages[3],userMessages[4]);
        Movies movies = new Movies();
        movies.addFilm(new Film("НАЗВАНИЕ","ОПИСАНИЕ","РЕЙТИНГ",List.of("драма")))  ;
        when(apiFilmMock.takeFilms(TypeOfFilmRequest.GENRE,"драма")).thenReturn(movies);
        LogicDialog logicDialog= new LogicDialog(apiFilmMock, dialogMock);
        logicDialog.startDialog(user);
        verify(apiFilmMock).takeFilms(TypeOfFilmRequest.GENRE,"драма");
    }
    @Test
    public void apiGenreTestFault(){
        ApiFilm apiFilmMock = mock(ApiFilm.class);
        Dialog dialogMock = mock(Dialog.class);
        User user = new User(1);
        UserMessage[] userMessages = {new UserMessage(user,"подскажи фильм"),new UserMessage(user,"жанр"),new UserMessage(user,"любой"),new UserMessage(user,"хватит"), new UserMessage(user,"стоп")};
        when(dialogMock.takeArg(user)).thenReturn(userMessages[0],userMessages[1],userMessages[2],userMessages[3],userMessages[4]);
        when(apiFilmMock.takeFilms(TypeOfFilmRequest.GENRE,"любой")).thenReturn(new Fault("Вы ввели некорректный жанр"));
        LogicDialog logicDialog= new LogicDialog(apiFilmMock, dialogMock);
        logicDialog.startDialog(user);
        verify(apiFilmMock).takeFilms(TypeOfFilmRequest.GENRE,"любой");
    }
}