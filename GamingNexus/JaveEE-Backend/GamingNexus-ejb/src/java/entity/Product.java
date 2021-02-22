    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jin yichen
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @NotNull
    //@Size(min = 0, max = 5000)
    @Lob
    @Column
    private String description;
    //@NotNull
    //@Size(min = 0, max = 5000)
    @Lob
    @Column
    private String computerRequirements;
    @NotNull
    //@Digits(integer = 1000000000, fraction = 2)
    private double price;
    //@Digits(integer = 1, fraction = 2)
    //@Min(0)
    //@Max(100)
    @NotNull
    private double averageRating;
    
    private LocalDate releaseDate;
    
    private long sales;

    @Lob
    @Column
    private String headerImage;
    @Lob
    @Column
    private String videoLink;

    @ManyToOne(optional = false)
    private Company company;
    @ManyToOne(optional = false)
    private Category category;
    @ManyToMany(mappedBy = "products")
    private List<Tag> tags;   
    @ManyToMany(mappedBy = "products")
    private List<Promotion> promotions;
    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;
    @OneToMany(mappedBy = "product")
    private List<OwnedItem> ownedItems;
    @OneToMany(mappedBy = "product")
    private List<Forum> forums;

    public Product() {
        tags = new ArrayList<>();
        ratings = new ArrayList<>();
        ownedItems = new ArrayList<>();
        forums = new ArrayList<>();
    }

    //for software games and products with videolink
    public Product(String name, String description, String computerRequirements, double price, double averageRating, LocalDate releaseDate, long sales, String headerImage, String videoLink) {
        this();
        this.name = name;
        this.description = description;
        this.computerRequirements = computerRequirements;
        this.price = price;
        this.averageRating = averageRating;
        this.releaseDate = releaseDate;
        this.sales = sales;
        this.headerImage = headerImage;
        this.videoLink = videoLink;
    }
    // for hardware
    public Product(String name, String description, double price, double averageRating, LocalDate releaseDate, long sales, String headerImage, String videoLink) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
        this.averageRating = averageRating;
        this.releaseDate = releaseDate;
        this.sales = sales;
        this.headerImage = headerImage;
        this.videoLink = videoLink;
    }
    

    public void addTag(Tag tagEntity) {
        if (tagEntity != null) {
            if (!this.tags.contains(tagEntity)) {
                this.tags.add(tagEntity);

                if (!tagEntity.getProducts().contains(this)) {
                    tagEntity.getProducts().add(this);
                }
            }
        }
    }

    public void removeTag(Tag tagEntity) {
        if (tagEntity != null) {
            if (this.tags.contains(tagEntity)) {
                this.tags.remove(tagEntity);

                if (tagEntity.getProducts().contains(this)) {
                    tagEntity.getProducts().remove(this);
                }
            }
        }
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the productId fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.Product[ id=" + productId + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the computerRequirements
     */
    public String getComputerRequirements() {
        return computerRequirements;
    }

    /**
     * @param computerRequirements the computerRequirements to set
     */
    public void setComputerRequirements(String computerRequirements) {
        this.computerRequirements = computerRequirements;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the averageRating
     */
    public double getAverageRating() {
        return averageRating;
    }

    /**
     * @param averageRating the averageRating to set
     */
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * @return the tag
     */
    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
    public List<Tag> getNormalTags() {
        List<Tag> normalTags = new ArrayList<>();
        for(Tag tag: tags) {
            if(tag.isIsGameTag() == false) {
                normalTags.add(tag);
            }
        }
        return normalTags;      
    }
    
    public void clearNormalTags() {
        
        ArrayList<Tag> tagsToRemove = new ArrayList<>();
        
        for(int i = 0; i <tags.size(); i++)
        {
            if(tags.get(i).isIsGameTag() == false) {
                tagsToRemove.add(tags.get(i));
            }
        }
        
        tags.removeAll(tagsToRemove);
        
//        for(Tag tag: tags) {
//            if(tag.isIsGameTag() == false) {
//                this.tags.remove(tag);
//            }
//        }
    }

    /**
     * @return the promotions
     */
    public List<Promotion> getPromotions() {
        return promotions;
    }

    /**
     * @param promotions the promotions to set
     */
    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    /**
     * @return the ratings
     */
    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * @param ratings the ratings to set
     */
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    /**
     * @return the shoppingCart
     */
    /**
     * @return the ownedItems
     */
    public List<OwnedItem> getOwnedItems() {
        return ownedItems;
    }

    /**
     * @param ownedItems the ownedItems to set
     */
    public void setOwnedItems(List<OwnedItem> ownedItems) {
        this.ownedItems = ownedItems;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        if (this.category != null) {
            if (this.category.getProducts().contains(this)) {
                this.category.getProducts().remove(this);
            }
        }

        this.category = category;

        if (this.category != null) {
            if (!this.category.getProducts().contains(this)) {
                this.category.getProducts().add(this);
            }
        }
    }

    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    /**
     * @return the sales
     */
    public long getSales() {
        return sales;
    }

    /**
     * @param sales the sales to set
     */
    public void setSales(long sales) {
        this.sales = sales;
    }

    /**
     * @return the releaseDate
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate the releaseDate to set
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return the headerImage
     */
    public String getHeaderImage() {
        return headerImage;
    }

    /**
     * @param headerImage the headerImage to set
     */
    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    /**
     * @return the videoLink
     */
    public String getVideoLink() {
        return videoLink;
    }

    /**
     * @param videoLink the videoLink to set
     */
    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    

}
