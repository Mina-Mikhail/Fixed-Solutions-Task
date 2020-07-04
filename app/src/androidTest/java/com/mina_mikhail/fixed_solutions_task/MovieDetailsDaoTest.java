package com.mina_mikhail.fixed_solutions_task;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.mina_mikhail.fixed_solutions_task.data.model.api.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao.MovieDetailsDao;
import com.mina_mikhail.fixed_solutions_task.di.component.DaggerTestComponent;
import com.mina_mikhail.fixed_solutions_task.di.component.TestComponent;
import com.mina_mikhail.fixed_solutions_task.di.module.TestModule;
import com.uber.autodispose.ScopeProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.uber.autodispose.AutoDispose.autoDisposable;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MovieDetailsDaoTest {

  @Inject
  public MovieDetailsDao movieDetailsDao;

  private CompositeDisposable disposable;

  @Before
  public void setUp() {
    TestComponent testComponent = DaggerTestComponent.builder()
        .context(InstrumentationRegistry.getInstrumentation().getTargetContext())
        .testModule(new TestModule())
        .build();

    testComponent.inject(this);

    disposable = new CompositeDisposable();
  }

  @Test
  public void insertMovieDetails() {
    int movieID = 101;
    String movieName = "My Favourite Movie";

    MovieDetails movieDetails = new MovieDetails();
    movieDetails.setId(movieID);
    movieDetails.setTitle(movieName);

    movieDetailsDao.insert(movieDetails);

    // Assert
    disposable.add(movieDetailsDao.getMovie(movieID)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribeWith(new DisposableSingleObserver<MovieDetails>() {
          @Override
          public void onSuccess(MovieDetails movieDetails) {
            if (movieDetails != null && movieDetails.getId() != 0) {
              assertEquals(movieDetails.getId(), movieID);
              assertEquals(movieDetails.getTitle(), movieName);
            }

            dispose();
          }

          @Override
          public void onError(Throwable e) {
            dispose();
          }
        }));
  }
}