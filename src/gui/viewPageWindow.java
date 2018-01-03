package gui;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import models.LikesModel;
import models.PagesModel;
import models.PostsModel;
import models.UsersModel;
import pojos.Pages;
import pojos.Posts;
import pojos.Users;

/**
 *
 * @author hassan
 */
public class viewPageWindow extends javax.swing.JFrame {

    private Pages currentPageObject;
    private JFrame previousWindow;
    private Users logedUser;
    private int currentPage;
    private List currentFetchedPagePosts;

    /**
     * Creates new form viewGroupWindow
     */
    public viewPageWindow() {
        initComponents();
    }

    public viewPageWindow(Pages p, JFrame frm, Users u) {
        this();
        this.currentPageObject = p;
        this.previousWindow = frm;
        pageNameLabel.setText(currentPageObject.getPageString());
        this.logedUser = u;
        if (!(p.getCreaterId() == logedUser.getId())) {
            deleteGroupButton.setVisible(false);
            createPostButton.setVisible(false);
        }
        
       // loadFirstPage();
    }

    private void loadFirstPage() {
        backButton.setVisible(false);
        nextButton.setVisible(false);
        currentPage = 0;
        pageLabel.setText("Page 1");
        currentFetchedPagePosts = models.PostsModel.getPosts(currentPageObject.getId(), 3, 0);
        if (currentFetchedPagePosts == null) {
            // no wall posts
            postPanel.setVisible(false);
            postPanel1.setVisible(false);
            // nextPagePossible = false;
            nextButton.setVisible(false);
            pageLabel.setText("No Posts");
        } else {
            if (currentFetchedPagePosts.size() == 1) {
                //   nextPagePossible = false;
                nextButton.setVisible(false);
                populatePostPanel((pojos.Posts) currentFetchedPagePosts.get(0));
                postPanel.setVisible(true);
                postPanel1.setVisible(false);
                pageLabel.setText("Last Page");
            } else {
                // nextPagePossible = true;
                if (!(models.PostsModel.getPosts(currentPageObject.getId(), 3, 1) == null)) {
                    nextButton.setVisible(true);

                }
                postPanel.setVisible(true);
                postPanel1.setVisible(true);
                populatePostPanel((pojos.Posts) currentFetchedPagePosts.get(0));
                populatePostPanel1((pojos.Posts) currentFetchedPagePosts.get(1));
            }
        }
    }

    private void nextPost() {
        currentFetchedPagePosts = models.PostsModel.getPosts(currentPageObject.getId(), 3, currentPage + 1);
        currentPage += 1;
        pageLabel.setText("Page " + (currentPage + 1));
        backButton.setVisible(true);

        if (currentFetchedPagePosts.size() == 1) {
            //   nextPagePossible = false;
            nextButton.setVisible(false);
            populatePostPanel((pojos.Posts) currentFetchedPagePosts.get(0));
            postPanel1.setVisible(false);
            pageLabel.setText("Last Page");
        }
        if (currentFetchedPagePosts.size() == 2) {
            // nextPagePossible = true;
            nextButton.setVisible(true);
            populatePostPanel((pojos.Posts) currentFetchedPagePosts.get(0));
            populatePostPanel1((pojos.Posts) currentFetchedPagePosts.get(1));
        }
        //check if more posts
        if (!morePosts()) {
            nextButton.setVisible(false);
            pageLabel.setText("Last Page");

        }

    }

    private void previousPost() {
        currentFetchedPagePosts = models.PostsModel.getPosts(currentPageObject.getId(), 3, currentPage - 1);
        currentPage -= 1;
        pageLabel.setText("Page " + (currentPage + 1));
        nextButton.setVisible(true);
        populatePostPanel((pojos.Posts) currentFetchedPagePosts.get(0));
        populatePostPanel1((pojos.Posts) currentFetchedPagePosts.get(1));
        postPanel.setVisible(true);
        postPanel1.setVisible(true);
        backButton.setVisible(true);
        if (currentPage == 0) {
            backButton.setVisible(false);
            pageLabel.setText("Page 1");
        }
    }

    private boolean morePosts() {
        List l = models.PostsModel.getPosts(currentPageObject.getId(), 3, currentPage + 1);
        return l != null;
    }

    public void setLikeText(int postNumber, String likeButtonText) {
        switch (postNumber) {
            case 0:
                likeButton.setText(likeButtonText);
                break;
            case 1:
                likeButton1.setText(likeButtonText);

        }
    }

    private void populatePostPanel(pojos.Posts p) {
        if (p.getUserId() == logedUser.getId() || logedUser.getId() == currentPageObject.getCreaterId()) {
            
            deleteButton.setVisible(true);
        } else {
            deleteButton.setVisible(false);
        }
        postTittleLabel.setText(UsersModel.getUser(p.getUserId()).getFullName());
        postLabel.setText("<html>" + p.getBody() + "</html>");
        dateLabel.setText(p.getPostDate().toString());
        if (!(p.getLikes() == null)) {
            likeLabel.setText(p.getLikes().size() + " Likes");
            System.out.println(p.getLikes());
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

    private void setLikesNumber(int panelNumber, int likes) {
        switch (panelNumber) {
            case 0:
                likeLabel.setText(likes + " Likes");
                break;
            case 1:
                likeLabel1.setText(likes + " Likes");

        }
    }

    private void populatePostPanel1(pojos.Posts p) {
        if (p.getUserId() == logedUser.getId() || logedUser.getId() == currentPageObject.getCreaterId()) {
            deleteButton1.setVisible(true);
        } else {
            deleteButton1.setVisible(false);
        }
        postTittleLabel1.setText(UsersModel.getUser(p.getUserId()).getFullName());
        postLabel1.setText("<html>" + p.getBody() + "</html>");
        dateLabel1.setText(p.getPostDate().toString());
        if (!(p.getLikes() == null)) {
            likeLabel1.setText(p.getLikes().size() + " Likes");
        } else {
            likeLabel1.setText("0 Likes");
        }
        boolean hasUserLikedPost = LikesModel.hasUserLikedPost(logedUser.getId(), p.getId(),
                p.getPostType());
        //  System.out.println(p.getLikes());

        if (hasUserLikedPost) {
            likeButton1.setText("Liked");
        } else {
            likeButton1.setText("Like");
        }
    }

    private boolean toggleLike(int postNumber) {
        pojos.Posts p;
        boolean buttonState = false;
        // if(postNumber == 1) {

        if (currentFetchedPagePosts.get(postNumber) == null) {
            return false;
        } else {
            p = (pojos.Posts) currentFetchedPagePosts.get(postNumber);
        }

        switch (postNumber) {
            case 0:
                buttonState = (likeButton.getText().compareTo("Liked") == 0);
                break;
            case 1:
                buttonState = (likeButton1.getText().compareTo("Liked") == 0);
        }

        if (buttonState) {
            if (!(LikesModel.removeLike(new pojos.Likes(logedUser.getId(), p.getId(), p.getPostType())))) {
                return false;
            }
            if (p.getLikes() == null) {

                setLikesNumber(postNumber, 0);
            } else {

                setLikesNumber(postNumber, p.getLikes().size());
            }
        } else {
            if (!(LikesModel.addLike(new pojos.Likes(logedUser.getId(), p.getId(), p.getPostType())))) {
                return false;
            }
            if (p.getLikes() == null) {

                setLikesNumber(postNumber, 1);
            } else {

                setLikesNumber(postNumber, p.getLikes().size());
            }
        }

        switch (postNumber) {
            case 0:
                likeButton.setText((buttonState) ? "Like" : "Liked");

                break;
            case 1:
                likeButton1.setText((buttonState) ? "Like" : "Liked");
        }

        // }
        // refreshLikes(p, postNumber);
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

        pageNameLabel = new javax.swing.JLabel();
        postPanel = new javax.swing.JPanel();
        postTittleLabel = new javax.swing.JLabel();
        postLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        commentsButton = new javax.swing.JButton();
        likeButton = new javax.swing.JButton();
        likeLabel = new javax.swing.JLabel();
        deleteButton = new javax.swing.JButton();
        postPanel1 = new javax.swing.JPanel();
        postTittleLabel1 = new javax.swing.JLabel();
        postLabel1 = new javax.swing.JLabel();
        dateLabel1 = new javax.swing.JLabel();
        commentsButton1 = new javax.swing.JButton();
        likeButton1 = new javax.swing.JButton();
        likeLabel1 = new javax.swing.JLabel();
        deleteButton1 = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        pageLabel = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        createPostButton = new javax.swing.JButton();
        deleteGroupButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                reloadData(evt);
            }
        });

        pageNameLabel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        pageNameLabel.setText("<Page Name >");

        postPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        postPanel.setAutoscrolls(true);

        postTittleLabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        postTittleLabel.setText("Post Title");

        postLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        postLabel.setText("<html>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec.</html>");
        postLabel.setMaximumSize(new java.awt.Dimension(306, 15));

        dateLabel.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        dateLabel.setText("Date&Time When post was posted");

        commentsButton.setText("Comments");
        commentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentsButtonActionPerformed(evt);
            }
        });

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
                                .addComponent(commentsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(likeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(postPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commentsButton)
                    .addComponent(likeButton)
                    .addComponent(deleteButton))
                .addContainerGap())
        );

        postPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        postPanel1.setAutoscrolls(true);

        postTittleLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        postTittleLabel1.setText("Post Title");

        postLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        postLabel1.setText("<html>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec.</html>");
        postLabel1.setMaximumSize(new java.awt.Dimension(306, 15));

        dateLabel1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        dateLabel1.setText("Date&Time When post was posted");

        commentsButton1.setText("Comments");
        commentsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentsButton1ActionPerformed(evt);
            }
        });

        likeButton1.setText("Like");
        likeButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                likeButton1ActionPerformed(evt);
            }
        });

        likeLabel1.setText("0 Likes");

        deleteButton1.setText("Delete Post");
        deleteButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout postPanel1Layout = new javax.swing.GroupLayout(postPanel1);
        postPanel1.setLayout(postPanel1Layout);
        postPanel1Layout.setHorizontalGroup(
            postPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(postPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(postLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(postPanel1Layout.createSequentialGroup()
                        .addGroup(postPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(postTittleLabel1)
                            .addComponent(dateLabel1)
                            .addComponent(likeLabel1)
                            .addGroup(postPanel1Layout.createSequentialGroup()
                                .addComponent(commentsButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(likeButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 174, Short.MAX_VALUE)))
                .addContainerGap())
        );
        postPanel1Layout.setVerticalGroup(
            postPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(postTittleLabel1)
                .addGap(5, 5, 5)
                .addComponent(dateLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(postLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(likeLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(postPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commentsButton1)
                    .addComponent(likeButton1)
                    .addComponent(deleteButton1))
                .addContainerGap())
        );

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        pageLabel.setText("Page 1");

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Back to Pages");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        createPostButton.setText("Create post");
        createPostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPostButtonActionPerformed(evt);
            }
        });

        deleteGroupButton.setText("Delete Page");
        deleteGroupButton.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                GroupShown(evt);
            }
        });
        deleteGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteGroupButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(postPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(postPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(185, 185, 185)
                                .addComponent(pageLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pageNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteGroupButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(createPostButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pageNameLabel))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(createPostButton)
                        .addComponent(deleteGroupButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(postPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(postPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextButton)
                    .addComponent(backButton)
                    .addComponent(pageLabel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void commentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentsButtonActionPerformed
        // TODO add your handling code here:
        viewCommentWindow w = new viewCommentWindow(this, logedUser, (Posts) currentFetchedPagePosts.get(0), 0);
        w.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_commentsButtonActionPerformed

    private void likeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_likeButtonActionPerformed
        if (!toggleLike(0)) {
            System.out.println("Error while liking the post");
        }
    }//GEN-LAST:event_likeButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (PostsModel.deletePost(((Posts) currentFetchedPagePosts.get(0)).getId())) {
            javax.swing.JOptionPane.showMessageDialog(null, "Post was deleted", "Delete Post", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            // previousWindow.setVisible(true);
            loadFirstPage();
            // this.dispose();
        } else {

            javax.swing.JOptionPane.showMessageDialog(null, "There was an error while deleting this post", "Delete Post", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void commentsButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentsButton1ActionPerformed
        viewCommentWindow w = new viewCommentWindow(this, logedUser, (Posts) currentFetchedPagePosts.get(1), 1);
        w.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_commentsButton1ActionPerformed

    private void likeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_likeButton1ActionPerformed
        if (!toggleLike(1)) {
            System.out.println("Error while liking the post");
        }
    }//GEN-LAST:event_likeButton1ActionPerformed

    private void deleteButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton1ActionPerformed
        if (PostsModel.deletePost(((Posts) currentFetchedPagePosts.get(1)).getId())) {
            javax.swing.JOptionPane.showMessageDialog(null, "Post was deleted", "Delete Post", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            // previousWindow.setVisible(true);
            loadFirstPage();
            // this.dispose();
        } else {

            javax.swing.JOptionPane.showMessageDialog(null, "There was an error while deleting this post", "Delete Post", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteButton1ActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        previousPost();
    }//GEN-LAST:event_backButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        nextPost();
    }//GEN-LAST:event_nextButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        previousWindow.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void createPostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPostButtonActionPerformed
        createPostWindow c = new createPostWindow(this, logedUser, currentPageObject.getId(), 3);
        c.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_createPostButtonActionPerformed

    private void reloadData(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_reloadData
     loadFirstPage();
        // TODO add your handling code here:
    }//GEN-LAST:event_reloadData

    private void deleteGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteGroupButtonActionPerformed
        if(PagesModel.deletePage(currentPageObject.getId())) {
            JOptionPane.showMessageDialog(null, "Page Deleted!");
            previousWindow.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Couldn't delete Page");
        }
        
    }//GEN-LAST:event_deleteGroupButtonActionPerformed

    private void GroupShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_GroupShown
        

    }//GEN-LAST:event_GroupShown

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
            java.util.logging.Logger.getLogger(viewPageWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewPageWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewPageWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewPageWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewPageWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton commentsButton;
    private javax.swing.JButton commentsButton1;
    private javax.swing.JButton createPostButton;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel dateLabel1;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton deleteButton1;
    private javax.swing.JButton deleteGroupButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton likeButton;
    private javax.swing.JButton likeButton1;
    private javax.swing.JLabel likeLabel;
    private javax.swing.JLabel likeLabel1;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel pageLabel;
    private javax.swing.JLabel pageNameLabel;
    private javax.swing.JLabel postLabel;
    private javax.swing.JLabel postLabel1;
    private javax.swing.JPanel postPanel;
    private javax.swing.JPanel postPanel1;
    private javax.swing.JLabel postTittleLabel;
    private javax.swing.JLabel postTittleLabel1;
    // End of variables declaration//GEN-END:variables
}
