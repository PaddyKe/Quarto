package interfaces;

import javafx.scene.paint.Paint;

public interface PlayerNotificatior {

	public void notifyPlayer(String notification);
	public void resetNotification();
	public void updateView();
	public void setPlayer(String name);
	public void showWin(int[] fields);
	public void setStyle(Paint p);

}
