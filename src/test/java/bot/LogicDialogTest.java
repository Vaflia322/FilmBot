package bot;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


class LogicDialogTest {
    @Test
    public void apiGenreDramaTest() {
        final ApiFilm apiFilmMock = mock(ApiFilm.class);
        final Dialog dialogMock = mock(Dialog.class);
        User user = new User(1);
        user.setApiRequest("characteristicType", "жанр");
        user.setApiRequest("request", "драма");
        Movies movies = new Movies();
        movies.addFilm(new Film("НАЗВАНИЕ", "ОПИСАНИЕ", "РЕЙТИНГ", List.of("драма")));
        movies.addFilm(new Film("НАЗВАНИЕ1", "ОПИСАНИЕ1", "РЕЙТИНГ1", List.of("драма")));
        when(apiFilmMock.takeFilms(TypeOfFilmRequest.GENRE, "драма")).thenReturn(movies);
        LogicDialog logicDialog = new LogicDialog(apiFilmMock, dialogMock);
        logicDialog.statusProcessing(user, UserState.REQUEST, "драма");
        Queue<Film> films = user.getFilms();
        Film film = films.remove();
        assertTrue(film.genres().contains("драма"));
        verify(apiFilmMock).takeFilms(TypeOfFilmRequest.GENRE, "драма");
    }

    @Test
    public void apiGenreTestFault() {
        final ApiFilm apiFilmMock = mock(ApiFilm.class);
        final Dialog dialogMock = mock(Dialog.class);
        User user = new User(1);
        user.setApiRequest("characteristicType", "жанр");
        user.setApiRequest("request", "любой");
        when(apiFilmMock.takeFilms(TypeOfFilmRequest.GENRE, "любой"))
                .thenReturn(new Fault("Вы ввели некорректный жанр"));
        LogicDialog logicDialog = new LogicDialog(apiFilmMock, dialogMock);
        logicDialog.statusProcessing(user, UserState.REQUEST, "любой");
        verify(apiFilmMock).takeFilms(TypeOfFilmRequest.GENRE, "любой");
    }
}