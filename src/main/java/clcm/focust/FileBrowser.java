package clcm.focust;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.swing.JFileChooser;


public class FileBrowser {
	
	public static File[] imageFiles;
	public static String storeDir = "";
	
	public static void FileFinder() {
		
		FutureTask<JFileChooser> futureFileChooser = new FutureTask<>(JFileChooser::new);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(futureFileChooser);
		
		
		JFileChooser fileChooser = null;
		try {
			fileChooser = futureFileChooser.get();
		} catch (InterruptedException | ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setDialogTitle("Select a Directory or File(s):");
		
		// abort if nothing selected or return the selected files 
		
		int returnValue = fileChooser.showOpenDialog(null);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			imageFiles = fileChooser.getSelectedFiles();
		} else {
			return;
		}
		
	
		
	}
	
}
