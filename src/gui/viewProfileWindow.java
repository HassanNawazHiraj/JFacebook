package gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import models.FriendsModel;
import models.LikesModel;
import models.PostsModel;
import models.UsersModel;
import pojos.Friends;
import pojos.Posts;
import pojos.Users;

/**
 *
 * @author hassan
 */
public class viewProfileWindow extends javax.swing.JFrame {

    private Users profileUser;
    private Users currentLogedUser;
    private JFrame previousWindow;
    private int currentPage = 0;
    // Users logedUser;
    List<Friends> friendList;
    DefaultListModel<String> friendListModel = new DefaultListModel<>();
    //  int nextPage = 0;
    // int currentPage = 0;
    // boolean nextPagePossible=false;
    List currentFetechedPosts;

    /**
     * Creates new form viewProfileWindow
     */
    public viewProfileWindow() {
        initComponents();
        this.addComponentListener(new ComponentAdapter() {
            // public void componentHidden(ComponentEvent e) {
            /* code run when component hidden*/
            // }

            @Override
            public void componentShown(ComponentEvent e) {
                loadFirstPage();
            }
        });

    }

    public viewProfileWindow(Users userObject, JFrame frm, Users logedUser) {
        this();
        this.profileUser = userObject;
        this.currentLogedUser = logedUser;
        this.setTitle("viewing " + profileUser.getFirstName() + "'s profile");
        System.out.println(profileUser);
        fullnameLabel.setText(profileUser.getFirstName() + " " + profileUser.getLastName());
        loadFirstPage();
        populateFriends();
        backButton.setVisible(false);
        previousWindow = frm;

       friendListLabel.setText(profileUser.getFirstName() + "'s Friends");
    }

    private void loadFirstPage() {
        backButton.setVisible(false);
        nextButton.setVisible(false);
        currentPage = 0;
        pageLabel.setText("Page 1");
        currentFetechedPosts = models.PostsModel.getPosts(profileUser.getId(), 1, 0);
        if (currentFetechedPosts == null) {
            // no wall posts
            postPanel.setVisible(false);
            postPanel1.setVisible(false);
            // nextPagePossible = false;
            nextButton.setVisible(false);
            pageLabel.setText("No Posts");
        } else {
            if (currentFetechedPosts.size() == 1) {
                //   nextPagePossible = false;
                nextButton.setVisible(false);
                populatePostPanel((pojos.Posts) currentFetechedPosts.get(0));
                postPanel.setVisible(true);
                postPanel1.setVisible(false);
                pageLabel.setText("Last Page");
            } else {
                // nextPagePossible = true;
                if (!(models.PostsModel.getPosts(profileUser.getId(), 1, 1) == null)) {
                    nextButton.setVisible(true);
                }

                postPanel.setVisible(true);
                postPanel1.setVisible(true);
                populatePostPanel((pojos.Posts) currentFetechedPosts.get(0));
                populatePostPanel1((pojos.Posts) currentFetechedPosts.get(1));
            }
        }
    }

    private void nextPost() {
        currentFetechedPosts = models.PostsModel.getPosts(profileUser.getId(), 1, currentPage + 1);
        currentPage += 1;
        pageLabel.setText("Page " + (currentPage + 1));
        backButton.setVisible(true);

        if (currentFetechedPosts.size() == 1) {
            //   nextPagePossible = false;
            nextButton.setVisible(false);
            postPanel.setVisible(true);
            populatePostPanel((pojos.Posts) currentFetechedPosts.get(0));
            postPanel1.setVisible(false);
            pageLabel.setText("Last Page");
        }
        if (currentFetechedPosts.size() == 2) {
            // nextPagePossible = true;
            nextButton.setVisible(true);

            postPanel.setVisible(true);
            postPanel1.setVisible(true);
            populatePostPanel((pojos.Posts) currentFetechedPosts.get(0));
            populatePostPanel1((pojos.Posts) currentFetechedPosts.get(1));
        }
        //check if more posts
        if (!morePosts()) {
            nextButton.setVisible(false);
            pageLabel.setText("Last Page");

        }

    }

    private void previousPost() {
        currentFetechedPosts = models.PostsModel.getPosts(profileUser.getId(), 1, currentPage - 1);
        currentPage -= 1;
        pageLabel.setText("Page " + (currentPage + 1));
        nextButton.setVisible(true);
        populatePostPanel((pojos.Posts) currentFetechedPosts.get(0));
        populatePostPanel1((pojos.Posts) currentFetechedPosts.get(1));
        postPanel.setVisible(true);
        postPanel1.setVisible(true);
        backButton.setVisible(true);
        if (currentPage == 0) {
            backButton.setVisible(false);
            pageLabel.setText("Page 1");
        }
    }

    private boolean morePosts() {
        List l = models.PostsModel.getPosts(profileUser.getId(), 1, currentPage + 1);
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
        if (!(p.getUserId() == currentLogedUser.getId())) {
            deleteButton.setVisible(false);
        } else {
            deleteButton.setVisible(true);
        }
        postPanel.setVisible(true);
        postTittleLabel.setText(UsersModel.getUser(p.getUserId()).getFullName());
        postLabel.setText("<html>" + p.getBody() + "</html>");
        dateLabel.setText(p.getPostDate().toString());
        if (!(p.getLikes() == null)) {
            likeLabel.setText(p.getLikes().size() + " Likes");
            System.out.println(p.getLikes());
        } else {
            likeLabel.setText("0 Likes");
        }
        boolean hasUserLikedPost = LikesModel.hasUserLikedPost(currentLogedUser.getId(), p.getId(),
                p.getPostType());
        //    System.out.println(p.getLikes());
        if (hasUserLikedPost) {
            likeButton.setText("Liked");
        } else {
            likeButton.setText("Like");
        }
    }

    private void populateFriends() {
        friendsListBox.removeAll();
        friendList = models.FriendsModel.getFriends(profileUser.getId());
        if (!(friendList == null)) {
            for (Friends f : friendList) {
                friendListModel.addElement(f.getFriendData().getFullName());
            }
        }
        friendsListBox.setModel(friendListModel);
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
        if (!(p.getUserId() == currentLogedUser.getId())) {
            deleteButton1.setVisible(false);
        } else {
            deleteButton1.setVisible(true);
        }
        postPanel1.setVisible(true);
        postTittleLabel1.setText(UsersModel.getUser(p.getUserId()).getFullName());
        postLabel1.setText("<html>" + p.getBody() + "</html>");
        dateLabel1.setText(p.getPostDate().toString());
        if (!(p.getLikes() == null)) {
            likeLabel1.setText(p.getLikes().size() + " Likes");
        } else {
            likeLabel1.setText("0 Likes");
        }
        boolean hasUserLikedPost = LikesModel.hasUserLikedPost(currentLogedUser.getId(), p.getId(),
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
        if (currentFetechedPosts.get(postNumber) == null) {
            return false;
        } else {
            p = (pojos.Posts) currentFetechedPosts.get(postNumber);
        }

        switch (postNumber) {
            case 0:
                buttonState = (likeButton.getText().compareTo("Liked") == 0);
                break;
            case 1:
                buttonState = (likeButton1.getText().compareTo("Liked") == 0);
        }

        if (buttonState) {
            if (!(LikesModel.removeLike(new pojos.Likes(currentLogedUser.getId(), p.getId(), p.getPostType())))) {
                return false;
            }
            if (p.getLikes() == null) {

                setLikesNumber(postNumber, 0);
            } else {

                setLikesNumber(postNumber, p.getLikes().size() - 1);
            }
        } else {
            if (!(LikesModel.addLike(new pojos.Likes(currentLogedUser.getId(), p.getId(), p.getPostType())))) {
                return false;
            }
            if (p.getLikes() == null) {

                setLikesNumber(postNumber, 1);
            } else {
                if (!(p.getLikes().size() == 1)) {

                    setLikesNumber(postNumber, p.getLikes().size());
                } else {

                    setLikesNumber(postNumber, p.getLikes().size());
                }
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

        fullnameLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        friendsListBox = new javax.swing.JList<>();
        postPanel1 = new javax.swing.JPanel();
        postTittleLabel1 = new javax.swing.JLabel();
        postLabel1 = new javax.swing.JLabel();
        dateLabel1 = new javax.swing.JLabel();
        commentsButton1 = new javax.swing.JButton();
        likeButton1 = new javax.swing.JButton();
        likeLabel1 = new javax.swing.JLabel();
        deleteButton1 = new javax.swing.JButton();
        friendListLabel = new javax.swing.JLabel();
        yourWallLabel = new javax.swing.JLabel();
        postPanel = new javax.swing.JPanel();
        postTittleLabel = new javax.swing.JLabel();
        postLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        commentsButton = new javax.swing.JButton();
        likeButton = new javax.swing.JButton();
        likeLabel = new javax.swing.JLabel();
        deleteButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        pageLabel = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        fullnameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fullnameLabel.setText("<firstname + lastname>");

        jButton1.setText("create a post");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        friendsListBox.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(friendsListBox);

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
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(likeLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(postPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commentsButton1)
                    .addComponent(likeButton1)
                    .addComponent(deleteButton1))
                .addContainerGap())
        );

        friendListLabel.setText("<FirstName>'s Friends :");

        yourWallLabel.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        yourWallLabel.setText("Profile");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(likeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(postPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commentsButton)
                    .addComponent(likeButton)
                    .addComponent(deleteButton))
                .addContainerGap())
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

        jButton2.setText("Remove Friend");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("back to profile");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fullnameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(friendListLabel)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                        .addComponent(yourWallLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fullnameLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(yourWallLabel)
                        .addComponent(jButton1)
                        .addComponent(jButton2)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(friendListLabel)
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(postPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(postPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nextButton)
                            .addComponent(backButton)
                            .addComponent(pageLabel))
                        .addGap(11, 11, 11)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        createPostWindow c = new createPostWindow(this, currentLogedUser, profileUser.getId(), 1);
        c.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void commentsButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentsButton1ActionPerformed
        viewCommentWindow w = new viewCommentWindow(this, currentLogedUser, (Posts) currentFetechedPosts.get(1), 1);
        w.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_commentsButton1ActionPerformed

    private void likeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_likeButton1ActionPerformed
        if (!toggleLike(1)) {
            System.out.println("Error while liking the post");
        }
    }//GEN-LAST:event_likeButton1ActionPerformed

    private void deleteButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton1ActionPerformed
        if (PostsModel.deletePost(((Posts) currentFetechedPosts.get(1)).getId())) {
            javax.swing.JOptionPane.showMessageDialog(null, "Post was deleted", "Delete Post", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            // previousWindow.setVisible(true);
            loadFirstPage();
            // this.dispose();
        } else {

            javax.swing.JOptionPane.showMessageDialog(null, "There was an error while deleting this post", "Delete Post", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteButton1ActionPerformed

    private void commentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentsButtonActionPerformed
        // TODO add your handling code here:
        viewCommentWindow w = new viewCommentWindow(this, currentLogedUser, (Posts) currentFetechedPosts.get(0), 0);
        w.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_commentsButtonActionPerformed

    private void likeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_likeButtonActionPerformed
        if (!toggleLike(0)) {
            System.out.println("Error while liking the post");
        }
    }//GEN-LAST:event_likeButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (PostsModel.deletePost(((Posts) currentFetechedPosts.get(0)).getId())) {
            javax.swing.JOptionPane.showMessageDialog(null, "Post was deleted", "Delete Post", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            // previousWindow.setVisible(true);
            loadFirstPage();
            // this.dispose();
        } else {

            javax.swing.JOptionPane.showMessageDialog(null, "There was an error while deleting this post", "Delete Post", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        nextPost();
    }//GEN-LAST:event_nextButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        previousPost();
    }//GEN-LAST:event_backButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        boolean removeFriend = FriendsModel.removeFriend(currentLogedUser.getId(), profileUser.getId());
        if(removeFriend) {
            removeFriend = FriendsModel.removeFriend(profileUser.getId(), currentLogedUser.getId());
        }
        
        if(removeFriend) {
            JOptionPane.showMessageDialog(null, "Friend Removed!");
            previousWindow.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Couldn't remove friend");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        previousWindow.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(viewProfileWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewProfileWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewProfileWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewProfileWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewProfileWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton commentsButton;
    private javax.swing.JButton commentsButton1;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel dateLabel1;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton deleteButton1;
    private javax.swing.JLabel friendListLabel;
    private javax.swing.JList<String> friendsListBox;
    private javax.swing.JLabel fullnameLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton likeButton;
    private javax.swing.JButton likeButton1;
    private javax.swing.JLabel likeLabel;
    private javax.swing.JLabel likeLabel1;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel pageLabel;
    private javax.swing.JLabel postLabel;
    private javax.swing.JLabel postLabel1;
    private javax.swing.JPanel postPanel;
    private javax.swing.JPanel postPanel1;
    private javax.swing.JLabel postTittleLabel;
    private javax.swing.JLabel postTittleLabel1;
    private javax.swing.JLabel yourWallLabel;
    // End of variables declaration//GEN-END:variables
}
