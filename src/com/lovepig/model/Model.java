package com.lovepig.model;

public class Model {
    public Model() {
        super();
    }

    public Model(String id) {
        super();
        this.id = id;
    }
    /**
     * 歌曲名称
     */
    public String name;
    /**
     * 歌曲id
     */
    public String id;
    /**
     * 是否被选中
     */
    public boolean checked;
    /**
     * 是否有索引
     */
    public char initial='*';
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Model other = (Model) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
