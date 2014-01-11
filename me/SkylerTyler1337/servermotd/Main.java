import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	
	 List<String> motd = null;
	 YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/ServerMOTD/config.yml"));
     int cur = 0;
     int len = 0;

	@Override
	public void onDisable() {
		// TODO: Place any custom disable code here.
		
	}
	
	@Override
	public void onEnable() {
		System.out.println(" ServerMOTD is Enabled");
		 createConfig();
         motd = config.getStringList("motd-changeto");
         len = motd.size();
         getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

                 @Override
                 public void run() {
                         cur++;
                 }
         }, 0L, config.getInt("motd-change-interval") * 20L);
         
         getServer().getPluginManager().registerEvents(this,this);
 }

	 private void createConfig() {
         motd = new ArrayList<String>();
         File f = new File("plugins/ServerMOTD/config.yml");
         if (!f.exists()) {
                 motd.add("This is a motd");
                 config.options().header("-- ServerMOTD By SkylerTyler1337 --");
                 config.set("motd-change-interval", 120);
                 config.set("motd-changeto", motd);
                 try {
                         config.save(f);
                 } catch (IOException e) {
                         e.printStackTrace();
                 }
         }
 }

 private String setMOTD() {
         if(cur < len) {
                 return motd.get(cur);
         } else if(cur == len) {
                 cur = 0;
                 return motd.get(cur);
         }
         return "Error";
 }

 @EventHandler
 public void onServerListPing(ServerListPingEvent e) {
         e.setMotd(ChatColor.translateAlternateColorCodes('&',setMOTD()));
 }
}
	
	
