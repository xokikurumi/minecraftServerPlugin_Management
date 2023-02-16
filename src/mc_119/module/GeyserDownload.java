package mc_119.module;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;

public class GeyserDownload {
	public static void download() {
		try {
			Bukkit.getLogger().info("Bukkit plugin \"Geyser\" Download start.");
			URL url = new URL("https://ci.opencollab.dev/job/GeyserMC/job/Geyser/job/master/lastSuccessfulBuild/artifact/bootstrap/spigot/build/libs/Geyser-Spigot.jar");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setAllowUserInteraction(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestMethod("GET");

			conn.connect();

			int httpStatusCode = conn.getResponseCode();
			if (httpStatusCode != HttpURLConnection.HTTP_OK) {
				throw new Exception("HTTP Status " + httpStatusCode);
			}

			String contentType = conn.getContentType();
			System.out.println("Content-Type: " + contentType);

			// Input Stream
			DataInputStream dataInStream = new DataInputStream(
					conn.getInputStream());

			// Output Stream
			DataOutputStream dataOutStream = new DataOutputStream(
					new BufferedOutputStream(
							new FileOutputStream(".\\plugins\\Geyser-Spigot.jar")));

			// Read Data
			byte[] b = new byte[4096];
			int readByte = 0;

			while (-1 != (readByte = dataInStream.read(b))) {
				dataOutStream.write(b, 0, readByte);
			}

			// Close Stream
			dataInStream.close();
			dataOutStream.close();
			Bukkit.getLogger().info("Bukkit plugin \"Geyser\" Download completed.");

		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
	}
}
