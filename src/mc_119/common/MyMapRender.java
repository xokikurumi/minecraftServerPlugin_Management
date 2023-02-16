package mc_119.common;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class MyMapRender extends MapRenderer{

	private String mapPath = "";

	public MyMapRender(String path) {
		// TODO 自動生成されたコンストラクター・スタブ
		mapPath = path;
	}

	@Override
	public void render(MapView arg0, MapCanvas canvas, Player player) {
		// TODO 自動生成されたメソッド・スタブ
		BufferedImage image = null;
	    try {
//	    	Bukkit.getLogger().info(new File("img.png").getAbsolutePath());
	        //画像を読み込む
	        image = ImageIO.read(new File(mapPath));

	        //画像を地図に貼り付ける
	        canvas.drawImage(0, 0, image);

	        //メモリを開放
	        image.flush();
	    }
	    catch(Exception e)
	    {
	        player.sendMessage(e.toString());
	        Bukkit.getLogger().info(e.getMessage());
	        return;
	    }
	}

}
