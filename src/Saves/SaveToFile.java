package Saves;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SaveToFile extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  JButton  btnSaveFile   = null;
    private  JButton  btnOpenDir    = null;
    private  JFileChooser fileChooser = null;
	//private BufferedImage bf;
    public SaveToFile() {
        btnOpenDir = new JButton("Открыть директорию");
        btnSaveFile = new JButton("Выбрать файл");
        fileChooser = new JFileChooser();
        addFileChooserListeners();
        JPanel contents = new JPanel();
        contents.add(btnOpenDir   );
        contents.add(btnSaveFile  );
        setContentPane(contents);
    	}
    
    private void addFileChooserListeners() {
    	
    	btnOpenDir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Выбор директории");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(SaveToFile.this);
                if (result == JFileChooser.APPROVE_OPTION )
                          JOptionPane.showMessageDialog(SaveToFile.this, fileChooser.getSelectedFile());
            }
        });
        
        btnSaveFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                   
            }
        });
    	
    }
    
    public void TrySave(BufferedImage bf) {
    	File file = new File("Untitled");
    	fileChooser.setDialogTitle("Сохранить файл");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(file);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter pngfilter = new FileNameExtensionFilter("png", "png");
        FileNameExtensionFilter jpgfilter = new FileNameExtensionFilter("jpg", "jpg");
        fileChooser.addChoosableFileFilter(pngfilter);
        fileChooser.addChoosableFileFilter(jpgfilter);
        if (fileChooser.showSaveDialog(SaveToFile.this) == JFileChooser.APPROVE_OPTION ) {
        	String type = fileChooser.getFileFilter().getDescription();
        	File file2 = new File(fileChooser.getSelectedFile() + "." + type);
        	try {  
                System.out.println(fileChooser.getSelectedFile() + "." + type);
                ImageIO.write(bf, type, file2);
            }  
            catch (Exception e1) {
            }
        }
    }
    
    /*public static void openDialog(Graphics g)
    {
        UIManager.put(
                 "FileChooser.saveButtonText", "���������");
        UIManager.put(
                 "FileChooser.cancelButtonText", "������");
        UIManager.put(
                 "FileChooser.fileNameLabelText", "������������ �����");
        UIManager.put(
                 "FileChooser.filesOfTypeLabelText", "���� ������");
        UIManager.put(
                 "FileChooser.lookInLabelText", "����������");
        UIManager.put(
                 "FileChooser.saveInLabelText", "��������� � ����������");
        UIManager.put(
                 "FileChooser.folderNameLabelText", "���� ����������");

        new SaveToFile();
    }
    
    public BufferedImage TryOpen() {
    	File file = new File("Untitled");
    	fileChooser.setDialogTitle("�������� �����");
    	
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(file);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter pngfilter = new FileNameExtensionFilter("png", "png");
        FileNameExtensionFilter jpgfilter = new FileNameExtensionFilter("jpg", "jpg");
        fileChooser.addChoosableFileFilter(pngfilter);
        fileChooser.addChoosableFileFilter(jpgfilter);
        if (fileChooser.showOpenDialog(SaveToFile.this) == JFileChooser.APPROVE_OPTION ) {
        try {
        	bf = ImageIO.read(fileChooser.getSelectedFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bf;
        }
        else return null;
    }*/
}
