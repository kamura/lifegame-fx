package lifegame;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;


public class Lifegame {
	private static final int SLEEP_MSEC = 200;
	private CellGrid cellGrid;
	private LifegameApp view;
	private ScheduledExecutorService scheduler;

	public Lifegame(LifegameApp view, int numOfRows, int numOfColumns) {
		cellGrid = new CellGrid(numOfRows, numOfColumns);
        this.view = view;
	}

	public void start() {
        stop();
		Runnable gotoNextGenerationTask = new Runnable() {
			@Override
			public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        view.updateCellGridView();
                        cellGrid.prepareNextGeneration();
                        cellGrid.gotoNextGeneration();
                    }
                });
			}
		};
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(gotoNextGenerationTask, 0, SLEEP_MSEC,
				TimeUnit.MILLISECONDS);
	}

	public void stop() {
        if (scheduler != null) {
            scheduler.shutdown();
            scheduler = null;
            view.updateCellGridView();
        }
	}

	public void reset() {
		stop();
		cellGrid.reset();
        view.updateCellGridView();
	}

	public CellGrid getCellGrid() {
		return cellGrid;
	}
}