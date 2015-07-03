package com.waygo.viewmodels;

import com.waygo.data.DataLayer;
import com.waygo.data.DataLayer.GetGitHubRepositorySearch;
import com.waygo.pojo.GitHubRepository;
import com.waygo.pojo.GitHubRepositorySearch;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static com.waygo.data.DataStreamNotification.fetchingError;
import static com.waygo.data.DataStreamNotification.fetchingStart;
import static com.waygo.data.DataStreamNotification.onNext;
import static com.waygo.viewmodels.RepositoriesViewModel.ProgressStatus.ERROR;
import static com.waygo.viewmodels.RepositoriesViewModel.ProgressStatus.IDLE;
import static com.waygo.viewmodels.RepositoriesViewModel.ProgressStatus.LOADING;
import static com.waygo.viewmodels.RepositoriesViewModel.toProgressStatus;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class RepositoriesViewModelTest {

    private RepositoriesViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new RepositoriesViewModel(
                mock(GetGitHubRepositorySearch.class),
                repositoryId -> Observable.just(mock(GitHubRepository.class)));
    }

    @Test
    public void testStartFetchingReportedAsLoading() {
        assertEquals(LOADING, toProgressStatus().call(fetchingStart()));
    }

    @Test
    public void testFetchingErrorReportedAsError() {
        assertEquals(ERROR, toProgressStatus().call(fetchingError()));
    }

    @Test
    public void testAnyValueReportedAsIdle() {
        GitHubRepositorySearch value = new GitHubRepositorySearch("", null);

        assertEquals(IDLE, toProgressStatus().call(onNext(value)));
    }

    @Test
    public void testTooManyRepositoriesAreCappedToFive() {
        TestSubscriber<List<GitHubRepository>> observer = new TestSubscriber<>();

        viewModel.toGitHubRepositoryList()
                 .call(Arrays.asList(1, 2, 3, 4, 5, 6))
                 .subscribe(observer);

        observer.awaitTerminalEvent();
        assertEquals("Invalid number of repositories",
                     5,
                     observer.getOnNextEvents().get(0).size());
    }

    @Test
    public void testTooLittleRepositoriesReturnThoseRepositories() {
        TestSubscriber<List<GitHubRepository>> observer = new TestSubscriber<>();

        viewModel.toGitHubRepositoryList()
                 .call(Arrays.asList(1, 2, 3))
                 .subscribe(observer);

        observer.awaitTerminalEvent();
        assertEquals("Invalid number of repositories",
                     3,
                     observer.getOnNextEvents().get(0).size());
    }

    @Test(expected = NullPointerException.class)
    public void testThrowsNullPointerExceptionWhenRepositoryIdIsNull() {
        //noinspection ConstantConditions
        viewModel.getGitHubRepositoryObservable(null);
    }

    @Test(expected = NullPointerException.class)
    public void testThrowsNullPointerExceptionWhenNetworkStatusIsNull() {
        //noinspection ConstantConditions
        viewModel.setNetworkStatusText(null);
    }

    @Test(expected = NullPointerException.class)
    public void testThrowsNullPointerExceptionWhenSearchStringIsNull() {
        //noinspection ConstantConditions,ConstantConditions
        viewModel.setSearchStringObservable(null);
    }

    @Test(expected = NullPointerException.class)
    public void testThrowsNullPointerExceptionWhenSelectedRepositoryIsNull() {
        //noinspection ConstantConditions
        viewModel.selectRepository(null);
    }

    @Test(expected = NullPointerException.class)
    public void testThrowsNullPointerExceptionConstructedWithNullRepositorySearch() {
        //noinspection ConstantConditions
        new RepositoriesViewModel(null, mock(DataLayer.GetGitHubRepository.class));
    }

    @Test(expected = NullPointerException.class)
    public void testThrowsNullPointerExceptionConstructedWithNullRepository() {
        //noinspection ConstantConditions
        new RepositoriesViewModel(mock(GetGitHubRepositorySearch.class), null);
    }

}
