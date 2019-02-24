package Measurer;

import java.io.IOException;
import java.io.OutputStream;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public final class Console extends OutputStream
	{
		private TextArea output;

		public Console(TextArea ta)
		{
			this.output = ta;
		}

		@Override
		public void write(int i) throws IOException
		{
			Platform.runLater(() -> output.appendText(String.valueOf((char) i)));
		}
	}