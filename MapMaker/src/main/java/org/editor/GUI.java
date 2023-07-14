package org.editor;

import org.editor.panels.TileSheetSelectorPanel;
import org.editor.tables.ComboItem;
import org.editor.tables.GraphicsPackages;
import org.editor.tables.TileImage;
import org.editor.tables.TileSheetData;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GUI {

    public Main main;
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
    JPanel editorPanel = new JPanel();
    ImagePanel editorImagePanel;
    JButton[][] editBlocks16 = new JButton[this.defaultScreenCol][this.defaultScreenRow];
    JButton[][] editBlocks32 = new JButton[this.defaultScreenCol * 2][this.defaultScreenRow * 2];
    JButton[][] editBlocks64 = new JButton[this.defaultScreenCol * 4][this.defaultScreenRow * 4];


    int maxTilePanelNum = 10;
    JPanel[] tilePanel = new JPanel[this.maxTilePanelNum];
    JLabel[] tileMessageLBL = new JLabel[maxTilePanelNum];
    public JPanel[] imagePanel = new JPanel[maxTilePanelNum];
    public JScrollPane[] imageScroll = new JScrollPane[maxTilePanelNum];
    public int nextTilePanel = 0;
    public BufferedImage tileSheetImage;
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
    boolean backGroundTileSelected = false;
    final int label_selected = 0;
    final int label_selectedImage = 1;
    final int label_imageNum = 2;
    final int label_colrow = 3;
    final int label_worldMapSize = 4;

    public TileSheetSelectorPanel tileSheetSelectorPanel = new TileSheetSelectorPanel(this);
    //public ImageSelectorPanel imageSelectorPanel = new ImageSelectorPanel(this);
    ImagePanel worldMapPanel;
    int worldMPsize;
    JLabel greenFrame;
    boolean mousePressed = false;
    Timer timer;
    public KeyHandler keyHandler = new KeyHandler(main, this);

    public static Font panelFont = new Font("SansSerif", Font.PLAIN, 14);
    public static Font borderFont = new Font("SansSerif", Font.BOLD,20);
    public Color borderColor = Color.BLACK;
    public JButton[] imageButtons;
    public JComboBox<ComboItem> graphicsPackage_CMB;
    public JPanel imageTileSheedPanel;
    public JScrollPane jScrollPane;
    int panelWidth = 570;
    int panelHeight = 700;


    public GUI(Main main) {
        this.main = main;
    }

    public void setupGUI() {

        createWindow();
        this.window.add(tileSheetSelectorPanel);
        createEditorPanel(this.editBlocks16, this.defaultScreenCol, this.defaultScreenRow);
        createEditorImagePanel();
        createWorldMapPanel();
        createToolBox();
        createTileSelectionPanel();
        createLabel();

        //setUpScrollPanel();
        this.window.setVisible(true);
        this.editorPanel.setVisible(true);
    }

    private void createWindow() {
        this.window = new JFrame("Quest for the Java gods Map Maker ");
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/earth.png")));
        this.window.setIconImage(icon.getImage());
        this.window.setDefaultCloseOperation(3);
        this.window.setLayout((LayoutManager) null);
        this.window.getContentPane().setBackground(new Color(0, 0, 0));

        this.window.setResizable(false);

        this.window.addKeyListener(new KeyHandler(this.main, this.main.gui));
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
                GUI.this.main.gui.tileSheetSelectorPanel.displayTileSheetSelectorPanel();
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
//        menuItem[i].addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                GUI.this.main.gridOnOff();
//            }
//        });
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

    private void createEditorPanel(JButton[][] editBlocks, int maxCol, int maxRow) {
        this.editorPanel = new JPanel();
        this.editorPanel.setLayout(new GridLayout(maxRow, maxCol));
        this.editorPanel.setOpaque(false);
        this.editorPanel.setVisible(false);
        this.window.add(this.editorPanel);

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
            this.editorPanel.add(editBlocks[col][row]);

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    private void createEditorImagePanel() {
        this.editorImagePanel = new ImagePanel();
        this.editorImagePanel.setBackground(Color.black);
        this.editorImagePanel.setOpaque(true);
        this.window.add(this.editorImagePanel);
    }

    public void setEditorSize() {

        this.tileSize = 48;
        this.editorPanelWidth = this.tileSize * this.defaultScreenCol;
        this.editorPanelHeight = this.tileSize * this.defaultScreenRow;

        this.worldMPsize = 300;

        int x = 1400;
        int y = 950;
        this.window.setSize(x, y);
        this.window.setLocationRelativeTo(null);

        x = 50;
        y = 60;
        this.editorImagePanel.setBounds(x, y, this.editorPanelWidth, this.editorPanelHeight);
        this.editorPanel.setBounds(x, y, this.editorPanelWidth, this.editorPanelHeight);

        // Add 10 card panels for tile set

        for (int i = 0; i < this.maxTilePanelNum; i++) {
            this.tilePanel[i].setBounds(50, 600, 700, 256);
        }

        this.midB.setBounds(1010, 648, 54, 48);
        this.midB.setFont(new Font(this.fontName, 0, 16));

        this.upB.setBounds(1010, 600, 54, 48);
        this.upB.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/arrow_up 50x50c.png"))));

        this.downB.setBounds(1010, 696, 54, 48);
        this.downB.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/arrow_down 50x50c.png"))));

        this.solidB.setBounds(1010, 744, 54, 48);
        this.solidB.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/brick-wall48x48.png"))));

        this.worldMapPanel.setBounds(1010, 130, 300, 300);

        this.label.get(0).setBounds(50, 545, 230, 50);
        this.label.get(1).setBounds(200, 545, 48, 48);
        this.label.get(2).setBounds(260, 545, 250, 48);
        this.label.get(3).setBounds(550, 495, 260, 48);
        this.label.get(4).setBounds(1010, 430, 300, 50);

        this.label.get(5).setBounds(50, 490, 230, 50);
        this.label.get(6).setBounds(200, 490, 48,48);


        this.zoomInB.setBounds(856, 396, 48, 48);
        this.zoomInB.setFont(this.font2);
        this.zoomInB.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/zoomin48.png"))));

        this.zoomOutB.setBounds(856,444, 48, 48);
        this.zoomOutB.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/zoomout48.png"))));

        this.bucketB.setBounds(904, 396, 48, 48);
        this.bucketB.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/bucket50x50b.png"))));

        x = (int) (904.0D * scale);
        y = (int) (444.0D * scale);
        this.eraserB.setBounds(904, 444, 48, 48);
        this.eraserB.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/eraser50x50b.png"))));

        this.moveUpB.setBounds(880, 207, this.tileSize, this.tileSize);
        this.moveUpB.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/arrow_up 50x50.png"))));

        this.moveDownB.setBounds(880, 303, this.tileSize, this.tileSize);
        this.moveDownB.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/arrow_down 50x50.png"))));

        this.moveLeftB.setBounds(830, 255, this.tileSize, this.tileSize);
        this.moveLeftB.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/arrow_left 50x50.png"))));

        this.moveRightB.setBounds(928, 255, this.tileSize, this.tileSize);
        this.moveRightB.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/arrow_right 50x50.png"))));


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
        this.label.get(0).setForeground(Color.white);
        this.label.get(0).setFont(this.font1.deriveFont(26.0F));
        this.label.get(0).setText("ForeGround:");
        this.window.add(this.label.get(0));

        this.label.add(new JLabel());
        this.window.add(this.label.get(1));

        this.label.add(new JLabel());
        this.label.get(2).setForeground(Color.white);
        this.label.get(2).setFont(this.font1.deriveFont(28.0F));
        this.window.add(this.label.get(2));

        this.label.add(new JLabel());
        this.label.get(3).setForeground(Color.white);
        this.label.get(3).setFont(this.font1.deriveFont(24.0F));
        this.label.get(3).setHorizontalAlignment(4);
        this.window.add(this.label.get(3));

        this.label.add(new JLabel());
        this.label.get(4).setForeground(Color.white);
        this.label.get(4).setFont(this.font1.deriveFont(24.0F));
        this.label.get(4).setHorizontalAlignment(0);
        this.window.add(this.label.get(4));

        this.label.add(new JLabel());
        this.label.get(5).setForeground(Color.white);
        this.label.get(5).setFont(this.font1.deriveFont(26.0F));
        this.label.get(5).setText("BackGround:");
        this.window.add(this.label.get(5));

        this.label.add(new JLabel());
        this.window.add(this.label.get(6));
    }

    private void createToolBox() {
        this.zoomInB = new BWButton();
        this.zoomInB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //GUI.this.main.zoomIn();
            }
        });
        this.window.add(this.zoomInB);

        this.zoomOutB = new BWButton();
        this.zoomOutB.setFont(this.font1);
        this.zoomOutB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //GUI.this.main.zoomOut();
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

    private void createTileSelectionPanel() {
        for (int i = 0; i < this.maxTilePanelNum; i++) {
            this.tilePanel[i] = new JPanel();
            this.tilePanel[i].setBackground(Color.blue);
            this.tilePanel[i].setLayout(null);
            this.tilePanel[i].setVisible(false);
            this.tileMessageLBL[i] = new JLabel("Please Add Tiles!");
            this.tileMessageLBL[i].setVisible(true);
            this.tileMessageLBL[i].setForeground(Color.WHITE);
            this.tileMessageLBL[i].setBounds((650 - 130) / 2,
            (256 - 20) / 2, 130,20);
            this.tilePanel[i].add(this.tileMessageLBL[i]);

            this.imagePanel[i] = new JPanel();

            this.window.add(this.tilePanel[i]);
        }
        this.tilePanel[0].setVisible(true);

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

    private void setUpScrollPanel(){

        for(int i = 0; i < 10; i++) {
            //imageScroll[i].add(imagePanel[i]);
        }
    }

    public void importTileSheet(int graphicsPackagesID, int tileSheetID, File filePath){

        TileSheetData tileSheetData = TileSheetData.getById(tileSheetID);

        JButton[] imageButtons = new JButton[256];
        TileImage tileImage = null;

        ImageIcon blankIcon = null;

        boolean ones = true;

        try {

            tileSheetImage = ImageIO.read(filePath.getAbsoluteFile());

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        int maxCol = tileSheetImage.getWidth() / 16;
        int maxRow = tileSheetImage.getHeight() / 16;

        imagePanel[nextTilePanel].setVisible(true);

        JLabel test = new JLabel("Test");
        test.setVisible(true);
        test.setBounds(23,23,150,20);
        test.setForeground(Color.WHITE);

        JPanel scrollHolds = new JPanel();
        scrollHolds.setBounds(5,5, 680, 256);
        scrollHolds.setBackground(Color.YELLOW);
        scrollHolds.setVisible(true);

        JPanel testPanel = new JPanel();

        JScrollPane imageScroll = new JScrollPane(testPanel);

        imageScroll.setBounds(5, 5, 650, 256);
        imageScroll.setVisible(true);
        imageScroll.setEnabled(true);

        testPanel.setBackground(Color.MAGENTA);

        testPanel.setLayout(new GridLayout(maxRow,maxCol));
        testPanel.setBackground(Color.WHITE);

        tileMessageLBL[nextTilePanel].setText("");

        int col = 0;
        int row = 0;
        int index = 0;
        int buttonIndex = 0;
        boolean isImage;
        int imageId = 0;

        while (col < maxCol && row < maxRow) {

            // test for blank or image

            if(index < tileSheetData.tileDataList.size() - 1) {
                isImage = tileSheetData.tileDataList.get(index).cellX == col && tileSheetData.tileDataList.get(index).cellY == row;
            } else {
                isImage = false;
            }

            if(isImage){
                // Make Image
                tileImage = new TileImage(main.getTileSheetCellImage(tileSheetImage, col, row));
                tileImage.icon = main.makeImageIcon(tileImage.image);
                tileImage.solid = tileSheetData.tileDataList.get(index).solid;
                main.currentTileSet.put(tileSheetData.tileDataList.get(index).tile_ID, tileImage);
                imageId = tileSheetData.tileDataList.get(index).tile_ID;

                index++;

            } else {
                // Make Blank
                if(!ones){

                    BufferedImage blankImage = main.getTileSheetCellImage(tileSheetImage, col, row);
                    blankIcon = main.makeImageIcon(blankImage);
                    ones = true;
                }
            }

            // Make Buttons
            imageButtons[buttonIndex] = new JButton();
            imageButtons[buttonIndex].setOpaque(false);
            imageButtons[buttonIndex].setBackground(Color.black);
            //imageButtons[buttonIndex].setActionCommand(String.valueOf(tileSheetData.tileDataList.get(imageWidth).tile_ID));
            imageButtons[buttonIndex].setIcon(isImage ? tileImage.icon : blankIcon);
            imageButtons[buttonIndex].setMargin(new Insets(0,0,0,0));
            imageButtons[buttonIndex].setEnabled(isImage);

            int finalImageId1 = imageId;
            imageButtons[buttonIndex].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!backGroundTileSelected) {
                        main.selectedForeGroundTileNum = finalImageId1;
                        GUI.this.label.get(1).setIcon(main.currentTileSet.get(finalImageId1).icon);
                        GUI.this.label.get(2).setText(String.valueOf(finalImageId1));
                    } else {
                        main.selectedBackGroundTileNum = finalImageId1;
                        GUI.this.label.get(5).setIcon(main.currentTileSet.get(finalImageId1).icon);
                        GUI.this.label.get(6).setText(String.valueOf(finalImageId1));
                    }
                }
            });


            //imagePanel[nextTilePanel].add(imageButtons[buttonIndex]);
            testPanel.add(imageButtons[buttonIndex]);

            buttonIndex++;

            col++;

            if (col == maxCol) {
                col = 0;
                row++;
            }
        }

        scrollHolds.add(imageScroll, BorderLayout.CENTER);
        tilePanel[nextTilePanel].add(imageScroll, BorderLayout.CENTER);
        scrollHolds.addKeyListener(new KeyHandler(this.main, this.main.gui));
        nextTilePanel++;
        imageScroll.updateUI();
        window.repaint();

    }

    public void displayTileSheetSelectorPanel(){

        ArrayList<GraphicsPackages> graphicsPackagesList = GraphicsPackages.getGraphicsPackagesList();

        // Setup panel size and center on screen
        panelWidth = 570;
        panelHeight = 700;
        int panelX = (1400 - panelWidth) / 2 ;
        int panelY = (900 - panelHeight) / 2;

        tileSheetSelectorPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
        tileSheetSelectorPanel.setLayout(null);
        tileSheetSelectorPanel.setBackground(Color.LIGHT_GRAY);
        tileSheetSelectorPanel.setVisible(true);

        TitledBorder titledBorder = BorderFactory.createTitledBorder
                (BorderFactory.createLineBorder(Color.gray, 1),
                        " Tile Sheet Selector Panel ", TitledBorder.CENTER,
                        TitledBorder.DEFAULT_POSITION,
                        borderFont, borderColor);
        tileSheetSelectorPanel.setBorder(titledBorder);

        // Setup how the ComboBox will display text
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items

        // Setup Label
        JLabel graphicsPackage_LBL = new JLabel("Graphics Package");
        graphicsPackage_LBL.setBounds((panelWidth - 200) / 2,35,200,20);
        graphicsPackage_LBL.setFont(panelFont);
        graphicsPackage_LBL.setHorizontalAlignment(JLabel.CENTER);
        graphicsPackage_LBL.setVisible(true);

        // Setup ComboBox
        graphicsPackage_CMB = new JComboBox<>();
        graphicsPackage_CMB.addItem(new ComboItem(-1,"Pick Graphics Package"));
        for(GraphicsPackages record : graphicsPackagesList){
            graphicsPackage_CMB.addItem(new ComboItem(record.getPackageID(), record.getName()));
        }

        graphicsPackage_CMB.setBounds((panelWidth - 300) / 2, 60, 300,20);
        graphicsPackage_CMB.setFont(panelFont);
        graphicsPackage_CMB.setRenderer(listRenderer);

        tileSheetSelectorPanel.add(graphicsPackage_LBL);
        tileSheetSelectorPanel.add(graphicsPackage_CMB);

        imageTileSheedPanel = new JPanel();
        jScrollPane = new JScrollPane(imageTileSheedPanel);
        imageTileSheedPanel.setLayout(new GridLayout(0,2));
        //imagePanel.setBounds((panelWidth - 512) / 2, 85, 512, 600);
        imageTileSheedPanel.setBackground(Color.WHITE);

        graphicsPackage_CMB.addActionListener(e -> {

            ComboItem imageId = (ComboItem) graphicsPackage_CMB.getSelectedItem();

            displayTileSheets(imageId.getID());
            window.repaint();

        });

    }

    public void displayTileSheets(int tileSheetIDs){

        // Make Image Pane
        imageTileSheedPanel.removeAll();

        ArrayList<TileSheetData> gpTileSheetData = TileSheetData.getTileSheetsForGP
                (Objects.requireNonNull(GraphicsPackages.getTileSheetDataArrayList(tileSheetIDs)));

        System.out.println("Size: " +  gpTileSheetData.size());
        imageButtons = new JButton[gpTileSheetData.size()];
        int index = 0;
        for(TileSheetData tileSheetImage : gpTileSheetData){

            File directory = new File("./resources/graphics_packages/" + tileSheetImage.path + tileSheetImage.tileSheetName );

            try {
                BufferedImage bufferedImage = ImageIO.read(new File(directory.toString()));
                System.out.println("Index: " + index);
                // Make buttons and to button panel
                imageButtons[index] = new JButton();
                imageButtons[index].setOpaque(false);
                imageButtons[index].setBackground(Color.black);
                //imageButtons[index].setActionCommand(String.valueOf(tileSheetImage.tileSheet_ID));
                imageButtons[index].setIcon(new ImageIcon(bufferedImage));
                imageButtons[index].setMargin(new Insets(0,0,0,0));
                imageButtons[index].addActionListener(e -> {

                    importTileSheet(tileSheetImage.packageID, tileSheetImage.tileSheet_ID, directory);

                    imageTileSheedPanel.removeAll();
                    imageTileSheedPanel.setVisible(false);
                    window.repaint();

                });

                imageTileSheedPanel.add(imageButtons[index]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            index++;

        }

        jScrollPane.setBounds((panelWidth - 542) / 2, 85, 542, 600);

        imageTileSheedPanel.add(jScrollPane, BorderLayout.CENTER);

        jScrollPane.updateUI();
        imageTileSheedPanel.repaint();
        window.repaint();
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
