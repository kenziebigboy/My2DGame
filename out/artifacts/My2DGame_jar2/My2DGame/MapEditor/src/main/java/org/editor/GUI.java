package org.editor;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class GUI {
    Main main;
    JFrame window;
    Font font1 = new Font("Times New Roman", 0, 20);
    Font font2 = new Font("Times New Roman", 0, 10);
    String fontName = "Times New Roman";
    final double small = 0.75D;
    final double medium = 1.0D;
    final double large = 1.5D;
    double scale = 1.0D;

    String about;

    int tileSize = 48;
    int defaultScreenCol = 16;
    int defaultScreenRow = 9;
    int maxScreenCol = this.defaultScreenCol;
    int maxScreenRow = this.defaultScreenRow;
    int editorPanelWidth;
    int editorPanelHeight;
    JPanel[] editorPanel = new JPanel[4];
    ImagePanel editorImagePanel;
    JButton[][] editBlocks16 = new JButton[this.defaultScreenCol][this.defaultScreenRow];
    JButton[][] editBlocks32 = new JButton[this.defaultScreenCol * 2][this.defaultScreenRow * 2];
    JButton[][] editBlocks64 = new JButton[this.defaultScreenCol * 4][this.defaultScreenRow * 4];


    int maxTilePanelNum = 10;
    JPanel[] tilePanel = new JPanel[this.maxTilePanelNum];
    JButton[] tileButton = new JButton[100 * this.maxTilePanelNum];
    int currentTilePanelNum = 0;
    BWButton midB;
    BWButton upB;
    BWButton downB;
    BWButton solidB;
    BWButton zoomInB;
    BWButton zoomOutB;
    ArrayList<JLabel> label = new ArrayList<>();
    BWButton bucketB;
    BWButton eraserB;
    BWButton moveUpB;
    BWButton moveDownB;
    BWButton moveLeftB;
    BWButton moveRightB;
    final int label_selected = 0;
    final int label_selectedImage = 1;
    final int label_imageNum = 2;
    final int label_colrow = 3;
    final int label_worldMapSize = 4;

    ImagePanel worldMapPanel;
    int worldMPsize;
    JLabel greenFrame;
    boolean mousePressed = false;
    Timer timer;

    public GUI(Main main) {
        this.main = main;
    }

    public void setupGUI() {
        this.about = "Simple 2D Tile Editor version " + this.main.version +
                "\n\nDeveloped by RyiSnow (2022)\n\nFeel free to use!\n\n\n" +
                "Update (Aug 27, 2022)\n- Added the Editor Size option.\n" +
                "- Fixed a tile data loading issue.\n" +
                "- Fixed a minor focus issue.\n" +
                "- Changed the zoom in/out icons";

        createWindow();
        createEditorPanel(1, this.editBlocks16, this.defaultScreenCol, this.defaultScreenRow);
        createEditorPanel(2, this.editBlocks32, this.defaultScreenCol * 2, this.defaultScreenRow * 2);
        createEditorPanel(3, this.editBlocks64, this.defaultScreenCol * 4, this.defaultScreenRow * 4);
        createEditorImagePanel();
        createWorldMapPanel();
        createToolBox();
        createTileSelectionPanel();
        createLabel();

        this.window.setVisible(true);
        this.editorPanel[1].setVisible(true);
    }

    public void setEditorSize(double scale) {
        this.scale = scale;
        this.main.saveConfig();
        this.tileSize = (int) (48.0D * scale);
        this.editorPanelWidth = this.tileSize * this.defaultScreenCol;
        this.editorPanelHeight = this.tileSize * this.defaultScreenRow;

        this.worldMPsize = (int) (300.0D * scale);


        int x = (int) (1400.0D * scale);
        int y = (int) (950.0D * scale);
        this.window.setSize(x, y);


        x = (int) (50.0D * scale);
        y = (int) (60.0D * scale);
        this.editorImagePanel.setBounds(x, y, this.editorPanelWidth, this.editorPanelHeight);
        this.editorPanel[1].setBounds(x, y, this.editorPanelWidth, this.editorPanelHeight);
        this.editorPanel[2].setBounds(x, y, this.editorPanelWidth, this.editorPanelHeight);
        this.editorPanel[3].setBounds(x, y, this.editorPanelWidth, this.editorPanelHeight);


        x = (int) (50.0D * scale);
        y = (int) (600.0D * scale);
        for (int i = 0; i < this.maxTilePanelNum; i++) {
            this.tilePanel[i].setBounds(x, y, this.tileSize * 20, this.tileSize * 5);
        }

        x = (int) (1010.0D * scale);
        y = (int) (648.0D * scale);
        int w = (int) (54.0D * scale);
        int h = (int) (48.0D * scale);
        this.midB.setBounds(x, y, w, h);
        this.midB.setFont(new Font(this.fontName, 0, (int) (16.0D * scale)));

        x = (int) (1010.0D * scale);
        y = (int) (600.0D * scale);
        this.upB.setBounds(x, y, w, h);
        this.upB.setIcon(new ImageIcon(getClass().getClassLoader().getResource("arrow_up 50x50c.png")));

        x = (int) (1010.0D * scale);
        y = (int) (696.0D * scale);
        this.downB.setBounds(x, y, w, h);
        this.downB.setIcon(new ImageIcon(getClass().getClassLoader().getResource("arrow_down 50x50c.png")));

        x = (int) (1010.0D * scale);
        y = (int) (744.0D * scale);
        this.solidB.setBounds(x, y, w, h);
        this.solidB.setIcon(new ImageIcon(getClass().getClassLoader().getResource("brick-wall48x48.png")));


        x = (int) (1010.0D * scale);
        y = (int) (130.0D * scale);
        w = (int) (300.0D * scale);
        h = (int) (300.0D * scale);
        this.worldMapPanel.setBounds(x, y, w, h);


        x = (int) (50.0D * scale);
        y = (int) (545.0D * scale);
        w = (int) (230.0D * scale);
        h = (int) (50.0D * scale);
        ((JLabel) this.label.get(0)).setBounds(x, y, w, h);
        x = (int) (200.0D * scale);
        y = (int) (545.0D * scale);
        w = (int) (48.0D * scale);
        h = (int) (48.0D * scale);
        ((JLabel) this.label.get(1)).setBounds(x, y, w, h);
        x = (int) (280.0D * scale);
        y = (int) (545.0D * scale);
        w = (int) (250.0D * scale);
        h = (int) (48.0D * scale);
        ((JLabel) this.label.get(2)).setBounds(x, y, w, h);
        x = (int) (550.0D * scale);
        y = (int) (495.0D * scale);
        w = (int) (260.0D * scale);
        h = (int) (48.0D * scale);
        ((JLabel) this.label.get(3)).setBounds(x, y, w, h);
        x = (int) (1010.0D * scale);
        y = (int) (430.0D * scale);
        w = (int) (300.0D * scale);
        h = (int) (50.0D * scale);
        ((JLabel) this.label.get(4)).setBounds(x, y, w, h);


        x = (int) (856.0D * scale);
        y = (int) (396.0D * scale);
        w = (int) (48.0D * scale);
        h = (int) (48.0D * scale);
        this.zoomInB.setBounds(x, y, w, h);
        this.zoomInB.setFont(this.font2);
        this.zoomInB.setIcon(new ImageIcon(getClass().getClassLoader().getResource("zoomin48.png")));

        x = (int) (856.0D * scale);
        y = (int) (444.0D * scale);
        this.zoomOutB.setBounds(x, y, w, h);
        this.zoomOutB.setIcon(new ImageIcon(getClass().getClassLoader().getResource("zoomout48.png")));

        x = (int) (904.0D * scale);
        y = (int) (396.0D * scale);
        this.bucketB.setBounds(x, y, w, h);
        this.bucketB.setIcon(new ImageIcon(getClass().getClassLoader().getResource("bucket50x50b.png")));

        x = (int) (904.0D * scale);
        y = (int) (444.0D * scale);
        this.eraserB.setBounds(x, y, w, h);
        this.eraserB.setIcon(new ImageIcon(getClass().getClassLoader().getResource("eraser50x50b.png")));

        x = (int) (880.0D * scale);
        y = (int) (207.0D * scale);
        this.moveUpB.setBounds(x, y, this.tileSize, this.tileSize);
        this.moveUpB.setIcon(new ImageIcon(getClass().getClassLoader().getResource("arrow_up 50x50.png")));

        x = (int) (880.0D * scale);
        y = (int) (303.0D * scale);
        this.moveDownB.setBounds(x, y, this.tileSize, this.tileSize);
        this.moveDownB.setIcon(new ImageIcon(getClass().getClassLoader().getResource("arrow_down 50x50.png")));

        x = (int) (830.0D * scale);
        y = (int) (255.0D * scale);
        this.moveLeftB.setBounds(x, y, this.tileSize, this.tileSize);
        this.moveLeftB.setIcon(new ImageIcon(getClass().getClassLoader().getResource("arrow_left 50x50.png")));

        x = (int) (928.0D * scale);
        y = (int) (255.0D * scale);
        this.moveRightB.setBounds(x, y, this.tileSize, this.tileSize);
        this.moveRightB.setIcon(new ImageIcon(getClass().getClassLoader().getResource("arrow_right 50x50.png")));

        this.main.scaleImage();
        this.main.setIcons();
        this.main.refreshEditorImage();
        this.main.refreshWorldImage();
    }

    private void createWindow() {
        this.window = new JFrame("Simple 2D Tile Editor version  " + this.main.version);
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("boy_down_1.png"));
        this.window.setIconImage(icon.getImage());
        this.window.setDefaultCloseOperation(3);
        this.window.setLayout((LayoutManager) null);
        this.window.getContentPane().setBackground(new Color(0, 0, 0));
        this.window.setResizable(false);

        this.window.addKeyListener(new KeyHandler(this.main));
        createWindowMenu();
    }

    private void createWindowMenu() {
        JMenuBar topMenuBar = new JMenuBar();
        this.window.setJMenuBar(topMenuBar);


        JMenu[] menu = new JMenu[6];
        JMenuItem[] menuItem = new JMenuItem[20];


        int m = 0;
        menu[m] = new JMenu("File");
        menu[m].setFont(this.font1);
        topMenuBar.add(menu[m]);

        int i = 0;
        menuItem[i] = new JMenuItem("New Project");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.createNewProject();
            }
        });
        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("Import Tile");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.selectDirectory("tile");
            }
        });
        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("Import Tile Sheet");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.selectDirectory("sheet");
            }
        });
        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("Save Tile Data");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.selectDirectory("savedata");
            }
        });
        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("Load Tile Data");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.selectDirectory("loaddata");
            }
        });
        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("Save Map");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.saveMap();
            }
        });
        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("Save Map As");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.saveMapAs();
            }
        });
        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("Load Map");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.loadMap();
            }
        });
        menu[m].add(menuItem[i]);


        m++;
        menu[m] = new JMenu("Editor Size");
        menu[m].setFont(this.font1);
        topMenuBar.add(menu[m]);

        i++;
        menuItem[i] = new JMenuItem("Small");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.setEditorSize(0.75D);
            }
        });
        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("Medium");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.setEditorSize(1.0D);
            }
        });
        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("Large");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.setEditorSize(1.5D);
            }
        });
        menu[m].add(menuItem[i]);


        m++;
        menu[m] = new JMenu("Map Size");
        menu[m].setFont(this.font1);
        topMenuBar.add(menu[m]);

        i++;
        menuItem[i] = new JMenuItem("50 x 50");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.showMapSizeNotification(50);
            }
        });
        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("100 x 100");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.showMapSizeNotification(100);
            }
        });
        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("250 x 250");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.showMapSizeNotification(250);
            }
        });
        menu[m].add(menuItem[i]);


        m++;
        menu[m] = new JMenu("View");
        menu[m].setFont(this.font1);
        topMenuBar.add(menu[m]);

        i++;
        menuItem[i] = new JMenuItem("Grid On/Off");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.gridOnOff();
            }
        });
        menu[m].add(menuItem[i]);


        m++;
        menu[m] = new JMenu("Tools");
        menu[m].setFont(this.font1);
        topMenuBar.add(menu[m]);

        i++;
        menuItem[i] = new JMenuItem("Splash On Entire Map");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.splashAll();
            }
        });
        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("Erase All Tiles On Map");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.eraseAll();
            }
        });
        menu[m].add(menuItem[i]);


        m++;
        menu[m] = new JMenu("Help");
        menu[m].setFont(this.font1);
        topMenuBar.add(menu[m]);

        i++;
        menuItem[i] = new JMenuItem("Shortcut List");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.showSubWindow("Keyboard Shortcut List",
                        "[E]  Zoom In\n[Q]  Zoom Out\n[W/A/S/D]  Move the edit position\n[Shift + W/A/S/D]  Move all the tiles\n[G]  Grid On/Off\n[Ctrl + S]  Save Map");
            }
        });


        menu[m].add(menuItem[i]);

        i++;
        menuItem[i] = new JMenuItem("About");
        menuItem[i].setFont(this.font1);
        menuItem[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.showSubWindow("About", GUI.this.about);
            }
        });
        menu[m].add(menuItem[i]);
    }

    private void createEditorImagePanel() {
        this.editorImagePanel = new ImagePanel();
        this.editorImagePanel.setBackground(Color.black);
        this.editorImagePanel.setOpaque(true);
        this.window.add(this.editorImagePanel);
    }

    private void createEditorPanel(int i, JButton[][] editBlocks, int maxCol, int maxRow) {
        this.editorPanel[i] = new JPanel();
        this.editorPanel[i].setLayout(new GridLayout(maxRow, maxCol));
        this.editorPanel[i].setOpaque(false);
        this.editorPanel[i].setVisible(false);
        this.window.add(this.editorPanel[i]);

        int col = 0;
        int row = 0;
        while (col < maxCol && row < maxRow) {

            editBlocks[col][row] = new JButton();
            editBlocks[col][row].setOpaque(false);
            editBlocks[col][row].setBackground(Color.black);
            editBlocks[col][row].setActionCommand(String.valueOf(col) + " " + row);
            editBlocks[col][row].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String colAndRow = e.getActionCommand();
                    String[] s = colAndRow.split(" ");
                    GUI.this.main.selectedCol = Integer.parseInt(s[0]);
                    GUI.this.main.selectedRow = Integer.parseInt(s[1]);
                    GUI.this.main.drawOnEditor();
                }
            });
            final int tempC = col;
            final int tempR = row;
            editBlocks[col][row].addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                }

                public void mousePressed(MouseEvent e) {
                    GUI.this.mousePressed = true;
                    GUI.this.main.selectedCol = tempC;
                    GUI.this.main.selectedRow = tempR;
                    GUI.this.main.drawOnEditor();
                }

                public void mouseReleased(MouseEvent e) {
                    GUI.this.mousePressed = false;
                }

                public void mouseEntered(MouseEvent e) {
                    if (GUI.this.mousePressed) {
                        GUI.this.main.selectedCol = tempC;
                        GUI.this.main.selectedRow = tempR;
                        GUI.this.main.drawOnEditor();
                    }
                    int worldCol = GUI.this.main.topLeftCol + tempC;
                    int worldRow = GUI.this.main.topLeftRow + tempR;
                    ((JLabel) GUI.this.label.get(3)).setText("Col:" + worldCol + "  Row:" + worldRow);
                }

                public void mouseExited(MouseEvent e) {
                }
            });
            this.editorPanel[i].add(editBlocks[col][row]);

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    private void createTileSelectionPanel() {
        for (int i = 0; i < this.maxTilePanelNum; i++) {
            this.tilePanel[i] = new JPanel();
            this.tilePanel[i].setBackground(Color.blue);
            this.tilePanel[i].setLayout(new GridLayout(5, 20));
            this.tilePanel[i].setVisible(false);
            this.window.add(this.tilePanel[i]);
        }
        this.tilePanel[0].setVisible(true);


        int tilePanelNum = 0;
        for (int j = 0; j < this.tileButton.length; j++) {

            final int tempBN = j;
            this.tileButton[j] = new JButton();
            this.tileButton[j].setBackground(Color.black);
            this.tileButton[j].setForeground(Color.white);
            this.tileButton[j].setActionCommand(String.valueOf(j));
            this.tileButton[j].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    int command = Integer.parseInt(e.getActionCommand());

                    GUI.this.main.selectedTileNum = command;

                    if (GUI.this.main.selectedTileNum < GUI.this.main.tiles.size()) {
                        ((JLabel) GUI.this.label.get(1)).setIcon(((Tile) GUI.this.main.tiles.get(GUI.this.main.selectedTileNum)).icon);
                    }


                    if (GUI.this.main.selectedTileNum < GUI.this.main.tiles.size()) {


                        if (GUI.this.main.settingSolid) {
                            if (!((Tile) GUI.this.main.tiles.get(command)).collision) {
                                ((Tile) GUI.this.main.tiles.get(command)).collision = true;
                                GUI.this.tileButton[tempBN].setBorder(BorderFactory.createLineBorder(Color.red, 1));
                            } else if (((Tile) GUI.this.main.tiles.get(command)).collision) {
                                ((Tile) GUI.this.main.tiles.get(command)).collision = false;
                                GUI.this.tileButton[tempBN].setBackground(Color.black);
                                GUI.this.tileButton[tempBN].setBorder(BorderFactory.createLineBorder(Color.gray, 1));
                            }
                        }


                        if (((Tile) GUI.this.main.tiles.get(command)).collision) {
                            ((JLabel) GUI.this.label.get(2)).setText("Num:" + command + " (Solid)");
                        } else {

                            ((JLabel) GUI.this.label.get(2)).setText("Num:" + command);
                        }
                    } else {

                        ((JLabel) GUI.this.label.get(2)).setText((String) null);
                    }
                    GUI.this.window.requestFocus();
                }
            });

            tilePanelNum = j / 100;
            this.tilePanel[tilePanelNum].add(this.tileButton[j]);
        }


        this.midB = new BWButton();
        this.midB.setText(String.valueOf(this.currentTilePanelNum));
        this.window.add(this.midB);

        this.upB = new BWButton();
        this.upB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (GUI.this.currentTilePanelNum > 0) {
                    GUI.this.currentTilePanelNum--;
                    GUI.this.tilePanel[GUI.this.currentTilePanelNum].setVisible(true);
                    GUI.this.tilePanel[GUI.this.currentTilePanelNum + 1].setVisible(false);
                    GUI.this.midB.setText(String.valueOf(GUI.this.currentTilePanelNum));
                }
                GUI.this.window.requestFocus();
            }
        });
        this.window.add(this.upB);

        this.downB = new BWButton();
        this.downB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (GUI.this.currentTilePanelNum < GUI.this.maxTilePanelNum - 1) {
                    GUI.this.currentTilePanelNum++;
                    GUI.this.tilePanel[GUI.this.currentTilePanelNum].setVisible(true);
                    GUI.this.tilePanel[GUI.this.currentTilePanelNum - 1].setVisible(false);
                    GUI.this.midB.setText(String.valueOf(GUI.this.currentTilePanelNum));
                }
                GUI.this.window.requestFocus();
            }
        });
        this.window.add(this.downB);

        this.solidB = new BWButton();
        this.solidB.setFont(this.font1);
        this.solidB.setToolTipText("Clicking a tile while the wall icon is red to change the tile's solid status.");
        this.solidB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!GUI.this.main.settingSolid) {
                    GUI.this.main.settingSolid = true;
                    GUI.this.solidB.setBackground(Color.red);
                    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("brick-wall48x48red.png"));
                    GUI.this.solidB.setIcon(icon);
                } else if (GUI.this.main.settingSolid) {
                    GUI.this.main.settingSolid = false;
                    GUI.this.solidB.setBackground(Color.black);
                    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("brick-wall48x48.png"));
                    GUI.this.solidB.setIcon(icon);
                }
            }
        });
        this.window.add(this.solidB);
    }

    private void createWorldMapPanel() {
        this.worldMapPanel = new ImagePanel();

        Border border = BorderFactory.createLineBorder(Color.white, 1);
        this.worldMapPanel.setBorder(border);
        this.worldMapPanel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                double ratio = GUI.this.worldMPsize / GUI.this.main.maxWorldCol;
                System.out.println("ratio:" + ratio);
                int uniCol = (int) (x / ratio);
                int uniRow = (int) (y / ratio);
                System.out.println("uniCol:" + uniCol);

                GUI.this.main.topLeftCol = uniCol - GUI.this.maxScreenCol / 2;
                GUI.this.main.topLeftRow = uniRow - GUI.this.maxScreenRow / 2;

                GUI.this.main.refreshEditorImage();
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        this.window.add(this.worldMapPanel);

        this.greenFrame = new JLabel();
        Border greenBorder = BorderFactory.createLineBorder(Color.green, 1);
        this.greenFrame.setBorder(greenBorder);
        this.worldMapPanel.add(this.greenFrame);
    }

    private void createLabel() {
        this.label.add(new JLabel());
        ((JLabel) this.label.get(0)).setForeground(Color.white);
        ((JLabel) this.label.get(0)).setFont(this.font1.deriveFont(26.0F));
        ((JLabel) this.label.get(0)).setText("Selected:");
        this.window.add(this.label.get(0));

        this.label.add(new JLabel());
        this.window.add(this.label.get(1));

        this.label.add(new JLabel());
        ((JLabel) this.label.get(2)).setForeground(Color.white);
        ((JLabel) this.label.get(2)).setFont(this.font1.deriveFont(28.0F));
        this.window.add(this.label.get(2));

        this.label.add(new JLabel());
        ((JLabel) this.label.get(3)).setForeground(Color.white);
        ((JLabel) this.label.get(3)).setFont(this.font1.deriveFont(24.0F));
        ((JLabel) this.label.get(3)).setHorizontalAlignment(4);
        this.window.add(this.label.get(3));

        this.label.add(new JLabel());
        ((JLabel) this.label.get(4)).setForeground(Color.white);
        ((JLabel) this.label.get(4)).setFont(this.font1.deriveFont(24.0F));
        ((JLabel) this.label.get(4)).setHorizontalAlignment(0);
        this.window.add(this.label.get(4));
    }

    private void createToolBox() {
        this.zoomInB = new BWButton();
        this.zoomInB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.zoomIn();
            }
        });
        this.window.add(this.zoomInB);

        this.zoomOutB = new BWButton();
        this.zoomOutB.setFont(this.font1);
        this.zoomOutB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.zoomOut();
            }
        });
        this.window.add(this.zoomOutB);

        this.bucketB = new BWButton();
        this.bucketB.setFont(this.font1);
        this.bucketB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.splash();
            }
        });
        this.window.add(this.bucketB);

        this.eraserB = new BWButton();
        this.eraserB.setFont(this.font1);
        this.eraserB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.main.erase();
            }
        });
        this.window.add(this.eraserB);


        this.moveUpB = new BWButton();
        this.moveUpB.setBorderPainted(false);
        this.moveUpB.setFont(this.font1);
        this.moveUpB.addMouseListener(new MouseListener() {
                                          public void mouseClicked(MouseEvent e) {
                                          }

                                          public void mousePressed(MouseEvent e) {
                                              GUI.this.mousePressed = true;
                                              GUI.this.timer = new Timer(60, new ActionListener() {
                                                  public void actionPerformed(ActionEvent e) {
                                                      //(GUI.null.access$0(GUI.null.this)).main.moveUp();
                                                  }
                                              });
                                              GUI.this.timer.restart();
                                          }

                                          public void mouseReleased(MouseEvent e) {
                                              GUI.this.mousePressed = false;
                                              GUI.this.timer.stop();
                                          }

                                          public void mouseEntered(MouseEvent e) {
                                          }

                                          public void mouseExited(MouseEvent e) {
                                          }
                                      }
        );
        this.window.add(this.moveUpB);

        this.moveDownB = new BWButton();
        this.moveDownB.setBorderPainted(false);
        this.moveDownB.setFont(this.font1);
        this.moveDownB.addMouseListener(new MouseListener() {
                                            public void mouseClicked(MouseEvent e) {
                                            }

                                            public void mousePressed(MouseEvent e) {
                                                GUI.this.mousePressed = true;
                                                GUI.this.timer = new Timer(60, new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        //(GUI.null.access$0(GUI.null.this)).main.moveDown();
                                                    }
                                                });
                                                GUI.this.timer.restart();
                                            }

                                            public void mouseReleased(MouseEvent e) {
                                                GUI.this.mousePressed = false;
                                                GUI.this.timer.stop();
                                            }

                                            public void mouseEntered(MouseEvent e) {
                                            }

                                            public void mouseExited(MouseEvent e) {
                                            }
                                        }
        );
        this.window.add(this.moveDownB);

        this.moveLeftB = new BWButton();
        this.moveLeftB.setBorderPainted(false);
        this.moveLeftB.setFont(this.font1);
        this.moveLeftB.addMouseListener(new MouseListener() {
                                            public void mouseClicked(MouseEvent e) {
                                            }

                                            public void mousePressed(MouseEvent e) {
                                                GUI.this.mousePressed = true;
                                                GUI.this.timer = new Timer(60, new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        //(GUI.null.access$0(GUI.null.this)).main.moveLeft();
                                                    }
                                                });
                                                GUI.this.timer.restart();
                                            }

                                            public void mouseReleased(MouseEvent e) {
                                                GUI.this.mousePressed = false;
                                                GUI.this.timer.stop();
                                            }

                                            public void mouseEntered(MouseEvent e) {
                                            }

                                            public void mouseExited(MouseEvent e) {
                                            }
                                        }
        );
        this.window.add(this.moveLeftB);

        this.moveRightB = new BWButton();
        this.moveRightB.setBorderPainted(false);
        this.moveRightB.setFont(this.font1);
        this.moveRightB.addMouseListener(new MouseListener() {
                                             public void mouseClicked(MouseEvent e) {
                                             }

                                             public void mousePressed(MouseEvent e) {
                                                 GUI.this.mousePressed = true;
                                                 GUI.this.timer = new Timer(60, new ActionListener() {
                                                     public void actionPerformed(ActionEvent e) {
                                                         //(GUI.null.access$0(GUI.null.this)).main.moveRight();
                                                     }
                                                 });
                                                 GUI.this.timer.restart();
                                             }

                                             public void mouseReleased(MouseEvent e) {
                                                 GUI.this.mousePressed = false;
                                                 GUI.this.timer.stop();
                                             }

                                             public void mouseEntered(MouseEvent e) {
                                             }

                                             public void mouseExited(MouseEvent e) {
                                             }
                                         }
        );
        this.window.add(this.moveRightB);
    }

    public void showSubWindow(String title, String description) {
        JFrame subWindow = new JFrame(title);
        subWindow.setSize(400, 400);
        subWindow.setResizable(false);
        subWindow.setLocationRelativeTo((Component) null);
        subWindow.setLayout((LayoutManager) null);
        subWindow.getContentPane().setBackground(Color.black);

        JTextArea jta = new JTextArea();
        jta.setBounds(20, 50, 360, 300);
        jta.setBackground(Color.black);
        jta.setForeground(Color.white);
        jta.setFont(this.font1);
        jta.setText(description);
        jta.setEditable(false);
        subWindow.add(jta);

        subWindow.setVisible(true);
    }
}


/* Location:              C:\Users\mcbig\Downloads\Simple2DTileEditor1.01(1)\!\com\simpleeditor\main\GUI.class
 * Java compiler version: 12 (56.0)
 * JD-Core Version:       1.1.3
 */