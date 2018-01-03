package gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.JFrame;
import models.CommentsModel;
import models.LikesModel;
import models.PostsModel;
import models.UsersModel;

/**
 *
 * @author hassan
 */
import pojos.Users;
import pojos.Posts;
import pojos.Comments;

public class viewCommentWindow extends javax.swing.JFrame {

    private JFrame previousWindow;
    private Users logedUser;
    private Posts currentPost;
    private int postNumber;
    private int currentPage;
    private List currentfetchedComments;

    /**
     * Creates new form viewCommentWindow
     */
    public viewCommentWindow() {
        initComponents();
        this.setTitle("View Comment");
        this.addComponentListener(new ComponentAdapter() {
            // public void componentHidden(ComponentEvent e) {
            /* code run when component hidden*/
            // }

            @Override
            public void componentShown(ComponentEvent e) {
                loadCommentsFirstPage();
            }
        });
    }

    public viewCommentWindow(JFrame frm, Users logedUser, Posts currentPost, int postNumber) {
        this();
        previousWindow = frm;
        this.logedUser = logedUser;
        this.currentPost = currentPost;
        populatePostPanel(currentPost);
        this.postNumber = postNumber;
        loadCommentsFirstPage();
        System.out.println(logedUser);
    }

    private void loadCommentsFirstPage() {
        backButton.setVisible(false);
        nextButton.setVisible(false);
        currentPage = 0;
        pageLabel.setText("Page 1");
        currentfetchedComments = models.CommentsModel.getComments(currentPost.getId(), 0);
        populateCommentSection(currentfetchedComments);
        if (!(currentfetchedComments == null)) {
            switch (currentfetchedComments.size()) {

                case 1:
                case 2:
                    pageLabel.setText("Last Page");
                    break;
                case 3:
                    if (!(models.CommentsModel.getComments(currentPost.getId(), 1) == null)) {
                        nextButton.setVisible(true);
                    }
            }
        } else {
            pageLabel.setText("No Comments");
        }
    }

    private void populateCommentSection(List comments) {

        commentPanel.setVisible(false);
        commentPanel1.setVisible(false);
        commentPanel2.setVisible(false);
        commentDeleteButton.setVisible(false);
        commentDeleteButton1.setVisible(false);
        commentDeleteButton2.setVisible(false);
        if (!(comments == null)) {
            for (int i = 0; i < comments.size(); i++) {
                Comments c = (Comments) comments.get(i);
                switch (i) {
                    case 0:
                        commentPanel.setVisible(true);
                        commentNameLabel.setText(c.getUsers().getFullName());
                        commentDateLabel.setText(c.getPostDate().toString());
                        commentDataLabel.setText(c.getMessage());
                        boolean hasUserLikedPost = LikesModel.hasUserLikedPost(logedUser.getId(), c.getId(), 4);
                        commentLikeButton.setText((hasUserLikedPost) ? "Liked" : "Like");
                        if (!(c.getLikes() == null)) {
                            commentLikeLabel.setText(c.getLikes().size() + " Likes");
                        }
                        if (logedUser.getId() == c.getUserId()) {
                            commentDeleteButton.setVisible(true);
                        }
                        break;
                    case 1:
                        commentPanel1.setVisible(true);
                        commentNameLabel1.setText(c.getUsers().getFullName());
                        commentDateLabel1.setText(c.getPostDate().toString());
                        commentDataLabel1.setText(c.getMessage());
                        boolean hasUserLikedPost1 = LikesModel.hasUserLikedPost(logedUser.getId(), c.getId(), 4);
                        commentLikeButton1.setText((hasUserLikedPost1) ? "Liked" : "Like");
                        if (!(c.getLikes() == null)) {
                            commentLikeLabel1.setText(c.getLikes().size() + " Likes");
                        }
                        if (logedUser.getId() == c.getUserId()) {
                            commentDeleteButton1.setVisible(true);
                        }
                        break;

                    case 2:
                        commentPanel2.setVisible(true);
                        commentNameLabel2.setText(c.getUsers().getFullName());
                        commentDateLabel2.setText(c.getPostDate().toString());
                        commentDataLabel2.setText(c.getMessage());
                        boolean hasUserLikedPost2 = LikesModel.hasUserLikedPost(logedUser.getId(), c.getId(), 4);
                        commentLikeButton2.setText((hasUserLikedPost2) ? "Liked" : "Like");
                        if (!(c.getLikes() == null)) {
                            commentLikeLabel2.setText(c.getLikes().size() + " Likes");
                        }
                        if (logedUser.getId() == c.getUserId()) {
                            commentDeleteButton2.setVisible(true);
                        }

                }
            }
        }

    }

    private void populatePostPanel(pojos.Posts p) {
        postTittleLabel.setText(UsersModel.getUser(p.getUserId()).getFullName());
        postLabel.setText("<html>" + p.getBody() + "</html>");
        dateLabel.setText(p.getPostDate().toString());
        if (!(p.getLikes() == null)) {
            likeLabel.setText(p.getLikes().size() + " Likes");
        } else {
            likeLabel.setText("0 Likes");

        }
        boolean hasUserLikedPost = LikesModel.hasUserLikedPost(logedUser.getId(), p.getId(),
                p.getPostType());
        //    System.out.println(p.getLikes());
        if (hasUserLikedPost) {
            likeButton.setText("Liked");
        } else {
            likeButton.setText("Like");
        }
    }

    private boolean toggleLike(Posts p) {
        //  pojos.Posts p;
        boolean buttonState;
        JFrame mainwindow;

        // if(postNumber == 1) {
        buttonState = (likeButton.getText().compareTo("Liked") == 0);

        if (buttonState) {
            if (!(LikesModel.removeLike(new pojos.Likes(logedUser.getId(), p.getId(), p.getPostType())))) {
                return false;
            }
            //   mainwindow.setLikeText(postNumber, "Like");

        } else {
            if (!(LikesModel.addLike(new pojos.Likes(logedUser.getId(), p.getId(), p.getPostType())))) {
                return false;
            }

        }
        likeButton.setText((buttonState) ? "Like" : "Liked");
        // mainwindow.setLikeText(postNumber, (buttonState) ? "Like" : "Liked");
//        if (previousWindow instanceof gui.mainWindow) {
//            ((gui.mainWindow) previousWindow).setLikeText(postNumber, (buttonState) ? "Like" : "Liked");
//        }
//        // } else {
//        if (previousWindow instanceof gui.viewProfileWindow) {
//            ((gui.viewProfileWindow) previousWindow).setLikeText(postNumber, (buttonState) ? "Like" : "Liked");
//        }
//        
//        if(previousWindow instanceof gui.viewCommentWindow) {
//                ((gui.viewCommentWindow) previousWindow).setLikeText(postNumber, (buttonState) ? "Like" : "Liked");
//      
//        }
        //  }
        // }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        postPanel = new javax.swing.JPanel();
        postTittleLabel = new javax.swing.JLabel();
        postLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        likeButton = new javax.swing.JButton();
        likeLabel = new javax.swing.JLabel();
        deleteButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        commentPanel = new javax.swing.JPanel();
        commentNameLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        commentDateLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        commentDataLabel = new javax.swing.JLabel();
        commentLikeButton = new javax.swing.JButton();
        commentDeleteButton = new javax.swing.JButton();
        commentLikeLabel = new javax.swing.JLabel();
        commentPanel1 = new javax.swing.JPanel();
        commentNameLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        commentDateLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        commentDataLabel1 = new javax.swing.JLabel();
        commentLikeButton1 = new javax.swing.JButton();
        commentDeleteButton1 = new javax.swing.JButton();
        commentLikeLabel1 = new javax.swing.JLabel();
        commentPanel2 = new javax.swing.JPanel();
        commentNameLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        commentDateLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        commentDataLabel2 = new javax.swing.JLabel();
        commentLikeButton2 = new javax.swing.JButton();
        commentDeleteButton2 = new javax.swing.JButton();
        commentLikeLabel2 = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        pageLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        postPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        postPanel.setAutoscrolls(true);

        postTittleLabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        postTittleLabel.setText("Post Title");

        postLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        postLabel.setText("<html>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec.</html>");
        postLabel.setMaximumSize(new java.awt.Dimension(306, 15));

        dateLabel.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        dateLabel.setText("Date&Time When post was posted");

        likeButton.setText("Like");
        likeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                likeButtonActionPerformed(evt);
            }
        });

        likeLabel.setText("0 Likes");

        deleteButton.setText("Delete Post");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout postPanelLayout = new javax.swing.GroupLayout(postPanel);
        postPanel.setLayout(postPanelLayout);
        postPanelLayout.setHorizontalGroup(
            postPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(postPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(postLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(postPanelLayout.createSequentialGroup()
                        .addGroup(postPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(postTittleLabel)
                            .addComponent(dateLabel)
                            .addComponent(likeLabel)
                            .addGroup(postPanelLayout.createSequentialGroup()
                                .addComponent(likeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        postPanelLayout.setVerticalGroup(
            postPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(postTittleLabel)
                .addGap(5, 5, 5)
                .addComponent(dateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(postLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(likeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(postPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(likeButton)
                    .addComponent(deleteButton))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Comments"));

        commentPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        commentNameLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        commentNameLabel.setText("poster Name");

        jLabel2.setText("posted at :");

        commentDateLabel.setText("[Show Date Here]");
        commentDateLabel.setToolTipText("");

        commentDataLabel.setText("<html> Lorem ipsum dolor sit amet, mei pertinacia scripserit an, qui error prompta euismod in, ex blandit persequeris referrentur vim at. </html>");

        commentLikeButton.setText("Like Comment");
        commentLikeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentLikeButtonActionPerformed(evt);
            }
        });

        commentDeleteButton.setText("Delete Comment");
        commentDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentDeleteButtonActionPerformed(evt);
            }
        });

        commentLikeLabel.setText("0 Likes");

        javax.swing.GroupLayout commentPanelLayout = new javax.swing.GroupLayout(commentPanel);
        commentPanel.setLayout(commentPanelLayout);
        commentPanelLayout.setHorizontalGroup(
            commentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(commentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(commentPanelLayout.createSequentialGroup()
                        .addComponent(commentNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commentDateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 361, Short.MAX_VALUE)
                        .addComponent(commentLikeLabel))
                    .addGroup(commentPanelLayout.createSequentialGroup()
                        .addGroup(commentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(commentDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(commentPanelLayout.createSequentialGroup()
                                .addComponent(commentLikeButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(commentDeleteButton)))
                        .addGap(0, 13, Short.MAX_VALUE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        commentPanelLayout.setVerticalGroup(
            commentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commentPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(commentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commentNameLabel)
                    .addComponent(jLabel2)
                    .addComponent(commentDateLabel)
                    .addComponent(commentLikeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commentDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(commentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commentLikeButton)
                    .addComponent(commentDeleteButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4))
        );

        commentPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        commentNameLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        commentNameLabel1.setText("poster Name");

        jLabel3.setText("posted at :");

        commentDateLabel1.setText("[Show Date Here]");
        commentDateLabel1.setToolTipText("");

        commentDataLabel1.setText("<html> Lorem ipsum dolor sit amet, mei pertinacia scripserit an, qui error prompta euismod in, ex blandit persequeris referrentur vim at. </html>");

        commentLikeButton1.setText("Like Comment");
        commentLikeButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentLikeButton1ActionPerformed(evt);
            }
        });

        commentDeleteButton1.setText("Delete Comment");
        commentDeleteButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentDeleteButton1ActionPerformed(evt);
            }
        });

        commentLikeLabel1.setText("0 Likes");

        javax.swing.GroupLayout commentPanel1Layout = new javax.swing.GroupLayout(commentPanel1);
        commentPanel1.setLayout(commentPanel1Layout);
        commentPanel1Layout.setHorizontalGroup(
            commentPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commentPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(commentPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(commentPanel1Layout.createSequentialGroup()
                        .addComponent(commentNameLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commentDateLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(commentLikeLabel1))
                    .addGroup(commentPanel1Layout.createSequentialGroup()
                        .addGroup(commentPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(commentDataLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(commentPanel1Layout.createSequentialGroup()
                                .addComponent(commentLikeButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(commentDeleteButton1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        commentPanel1Layout.setVerticalGroup(
            commentPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commentPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(commentPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commentNameLabel1)
                    .addComponent(jLabel3)
                    .addComponent(commentDateLabel1)
                    .addComponent(commentLikeLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commentDataLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(commentPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commentLikeButton1)
                    .addComponent(commentDeleteButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5))
        );

        commentPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        commentNameLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        commentNameLabel2.setText("poster Name");

        jLabel6.setText("posted at :");

        commentDateLabel2.setText("[Show Date Here]");
        commentDateLabel2.setToolTipText("");

        commentDataLabel2.setText("<html> Lorem ipsum dolor sit amet, mei pertinacia scripserit an, qui error prompta euismod in, ex blandit persequeris referrentur vim at. </html>");

        commentLikeButton2.setText("Like Comment");
        commentLikeButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentLikeButton2ActionPerformed(evt);
            }
        });

        commentDeleteButton2.setText("Delete Comment");
        commentDeleteButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentDeleteButton2ActionPerformed(evt);
            }
        });

        commentLikeLabel2.setText("0 Likes");

        javax.swing.GroupLayout commentPanel2Layout = new javax.swing.GroupLayout(commentPanel2);
        commentPanel2.setLayout(commentPanel2Layout);
        commentPanel2Layout.setHorizontalGroup(
            commentPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commentPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(commentPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(commentPanel2Layout.createSequentialGroup()
                        .addComponent(commentNameLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commentDateLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(commentLikeLabel2))
                    .addGroup(commentPanel2Layout.createSequentialGroup()
                        .addGroup(commentPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(commentDataLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(commentPanel2Layout.createSequentialGroup()
                                .addComponent(commentLikeButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(commentDeleteButton2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        commentPanel2Layout.setVerticalGroup(
            commentPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commentPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(commentPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commentNameLabel2)
                    .addComponent(jLabel6)
                    .addComponent(commentDateLabel2)
                    .addComponent(commentLikeLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commentDataLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(commentPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commentLikeButton2)
                    .addComponent(commentDeleteButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7))
        );

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        pageLabel.setText("Page 1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(commentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(commentPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(commentPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(231, 231, 231)
                        .addComponent(pageLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nextButton)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(commentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commentPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(commentPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextButton)
                    .addComponent(backButton)
                    .addComponent(pageLabel))
                .addContainerGap())
        );

        jButton1.setText("Add comment");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(postPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(postPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void likeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_likeButtonActionPerformed
        if (!toggleLikeForPost()) {
            System.out.println("Error while liking the post");
        }
    }//GEN-LAST:event_likeButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        previousWindow.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        createCommentWindow c = new createCommentWindow(this, logedUser, currentPost.getId());
        c.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private boolean toggleLikeForPost() {

        boolean buttonState = false;
        // if(postNumber == 1) {
        if (currentPost == null) {
            return false;
        }

        buttonState = (likeButton.getText().compareTo("Liked") == 0);

//rewrite this function
        if (buttonState) {
            if (!(LikesModel.removeLike(new pojos.Likes(logedUser.getId(), currentPost.getId(), currentPost.getPostType())))) {
                return false;
            }

            if (currentPost.getLikes() == null) {
                likeLabel.setText("0 Likes");
            } else {

                likeLabel.setText((currentPost.getLikes().size()) + " Likes");
            }

        } else {
            if (!(LikesModel.addLike(new pojos.Likes(logedUser.getId(), currentPost.getId(), currentPost.getPostType())))) {
                return false;
            }
            if (currentPost.getLikes() == null) {

                likeLabel.setText("1 Likes");
            } else {
                
                likeLabel.setText((currentPost.getLikes().size()) + " Likes");
            }
        }

       
                likeButton.setText((buttonState) ? "Like" : "Liked");
                

        // }
        return true;
    }

    private boolean toggleLike(int commentNumber) {
        Comments c;

        boolean buttonState = false;
        // if(postNumber == 1) {
        if (currentfetchedComments.get(commentNumber) == null) {
            return false;
        } else {
            c = (Comments) currentfetchedComments.get(commentNumber);

        }

        switch (commentNumber) {
            case 0:
                buttonState = (commentLikeButton.getText().compareTo("Liked") == 0);
                break;
            case 1:
                buttonState = (commentLikeButton1.getText().compareTo("Liked") == 0);
                break;
            case 2:
                buttonState = (commentLikeButton2.getText().compareTo("Liked") == 0);
        }
//rewrite this function
        if (buttonState) {
            if (!(LikesModel.removeLike(new pojos.Likes(logedUser.getId(), ((Comments) currentfetchedComments.get(commentNumber)).getId(), 4)))) {
                return false;
            }
            if (c.getLikes() == null) {

                setLikesNumber(commentNumber, 0);
            } else {

                setLikesNumber(commentNumber, c.getLikes().size() - 1);
            }

        } else {
            if (!(LikesModel.addLike(new pojos.Likes(logedUser.getId(), ((Comments) currentfetchedComments.get(commentNumber)).getId(), 4)))) {
                return false;
            }
            if (c.getLikes() == null) {

                setLikesNumber(commentNumber, 1);
            } else {
                setLikesNumber(commentNumber, c.getLikes().size());
            }
        }

        switch (commentNumber) {
            case 0:
                commentLikeButton.setText((buttonState) ? "Like" : "Liked");
                break;
            case 1:
                commentLikeButton1.setText((buttonState) ? "Like" : "Liked");
                break;
            case 2:
                commentLikeButton2.setText((buttonState) ? "Like" : "Liked");
        }

        // }
        return true;
    }

    private void setLikesNumber(int panelNumber, int likes) {
        switch (panelNumber) {
            case 0:
                commentLikeLabel.setText(likes + " Likes");
                break;
            case 1:
                commentLikeLabel1.setText(likes + " Likes");
                break;
            case 2:
                commentLikeLabel2.setText(likes + " Likes");

        }
    }

    private void nextComment() {
        currentfetchedComments = models.CommentsModel.getComments(currentPost.getId(), currentPage + 1);
        currentPage += 1;
        pageLabel.setText("Page " + (currentPage + 1));
        backButton.setVisible(true);

        if (currentfetchedComments.size() == 1 || currentfetchedComments.size() == 2) {
            //   nextPagePossible = false;
            nextButton.setVisible(false);
            //populatePostPanel((pojos.Posts) currentFetechedPosts.get(0));
            populateCommentSection(currentfetchedComments);
            //postPanel1.setVisible(false);
            pageLabel.setText("Last Page");
        }
        if (currentfetchedComments.size() == 3) {
            // nextPagePossible = true;
            nextButton.setVisible(true);
            populateCommentSection(currentfetchedComments);
        }
        //check if more posts
        if (!moreComments()) {
            nextButton.setVisible(false);
            pageLabel.setText("Last Page");

        }

    }

    private void previousComment() {
        currentfetchedComments = models.CommentsModel.getComments(currentPost.getId(), currentPage - 1);
        currentPage -= 1;
        pageLabel.setText("Page " + (currentPage + 1));
        nextButton.setVisible(true);
        populateCommentSection(currentfetchedComments);
        // postPanel.setVisible(true);
        //postPanel1.setVisible(true);
        backButton.setVisible(true);
        if (currentPage == 0) {
            backButton.setVisible(false);
            pageLabel.setText("Page 1");
        }
    }

    private boolean moreComments() {
        return models.CommentsModel.getComments(currentPost.getId(), currentPage + 1) != null;
        //  return l != null;
    }

    private boolean deleteComment(int postId) {
        if (CommentsModel.deleteComment(postId)) {
            javax.swing.JOptionPane.showMessageDialog(null, "Comment was deleted", "Delete Comment", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            loadCommentsFirstPage();
            return true;

        } else {

            javax.swing.JOptionPane.showMessageDialog(null, "There was an error while deleting this comment", "Delete Comment", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // return false;
    }

    public boolean deleteCommentNumber(int commentNumber) {
        return deleteComment(((Comments) currentfetchedComments.get(commentNumber)).getId());
        // System.out.println(((Comments) currentfetchedComments.get(commentNumber)).getMessage());
        //return false;
    }

    public void setLikeText(int postNumber, String likeButtonText) {
        switch (postNumber) {
            case 0:
                commentLikeButton.setText(likeButtonText);
                break;
            case 1:
                commentLikeButton1.setText(likeButtonText);
                break;
            case 2:
                commentLikeButton2.setText(likeButtonText);
        }
    }

    private void commentLikeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentLikeButtonActionPerformed
        if (!(toggleLike(0))) {
            javax.swing.JOptionPane.showMessageDialog(null, "Couldn't like this comment", "Like", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_commentLikeButtonActionPerformed

    private void commentLikeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentLikeButton1ActionPerformed

        if (!(toggleLike(1))) {
            javax.swing.JOptionPane.showMessageDialog(null, "Couldn't like this comment", "Like", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_commentLikeButton1ActionPerformed

    private void commentLikeButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentLikeButton2ActionPerformed

        if (!(toggleLike(2))) {
            javax.swing.JOptionPane.showMessageDialog(null, "Couldn't like this comment", "Like", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_commentLikeButton2ActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (PostsModel.deletePost(currentPost.getId())) {
            javax.swing.JOptionPane.showMessageDialog(null, "Post was deleted", "Delete Post", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            previousWindow.setVisible(true);
            this.dispose();
        } else {

            javax.swing.JOptionPane.showMessageDialog(null, "There was an error while deleting this post", "Delete Post", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        nextComment();
    }//GEN-LAST:event_nextButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        previousComment();
    }//GEN-LAST:event_backButtonActionPerformed

    private void commentDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentDeleteButtonActionPerformed
        deleteComment(((pojos.Comments) currentfetchedComments.get(0)).getId());
    }//GEN-LAST:event_commentDeleteButtonActionPerformed

    private void commentDeleteButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentDeleteButton1ActionPerformed
        deleteComment(((pojos.Comments) currentfetchedComments.get(1)).getId());        // TODO add your handling code here:
    }//GEN-LAST:event_commentDeleteButton1ActionPerformed

    private void commentDeleteButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentDeleteButton2ActionPerformed
        deleteComment(((pojos.Comments) currentfetchedComments.get(2)).getId());        // TODO add your handling code here:
    }//GEN-LAST:event_commentDeleteButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(viewCommentWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewCommentWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewCommentWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewCommentWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewCommentWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel commentDataLabel;
    private javax.swing.JLabel commentDataLabel1;
    private javax.swing.JLabel commentDataLabel2;
    private javax.swing.JLabel commentDateLabel;
    private javax.swing.JLabel commentDateLabel1;
    private javax.swing.JLabel commentDateLabel2;
    private javax.swing.JButton commentDeleteButton;
    private javax.swing.JButton commentDeleteButton1;
    private javax.swing.JButton commentDeleteButton2;
    private javax.swing.JButton commentLikeButton;
    private javax.swing.JButton commentLikeButton1;
    private javax.swing.JButton commentLikeButton2;
    private javax.swing.JLabel commentLikeLabel;
    private javax.swing.JLabel commentLikeLabel1;
    private javax.swing.JLabel commentLikeLabel2;
    private javax.swing.JLabel commentNameLabel;
    private javax.swing.JLabel commentNameLabel1;
    private javax.swing.JLabel commentNameLabel2;
    private javax.swing.JPanel commentPanel;
    private javax.swing.JPanel commentPanel1;
    private javax.swing.JPanel commentPanel2;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton likeButton;
    private javax.swing.JLabel likeLabel;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel pageLabel;
    private javax.swing.JLabel postLabel;
    private javax.swing.JPanel postPanel;
    private javax.swing.JLabel postTittleLabel;
    // End of variables declaration//GEN-END:variables
}
