

public class Sound implements Runnable {

	private String name;

	public Sound(String s) {
		name = s;
	}

	EasySound sound;

	@Override
	public void run() {
		try {
			sound = new EasySound(name);
			while (true) {
				sound.play();
			}
		} catch (Exception e) {

		}
	}

	public void stop() {
		try {
			sound.stop();
		} catch (Exception e) {

		}
	}

	public void start() {
		try {
			sound.startAgain();
		} catch (Exception e) {

		}
	}

}
