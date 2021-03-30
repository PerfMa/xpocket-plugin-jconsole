package com.perfma.xpocket.plugin.jconsole.cli.handler;

/**
 * @author xinxian
 **/
public class MemoryPool {
    private String name;
    private String simpleName;
    private String type;
    private Long used;
    private Long commited;
    private Long max;

    public static MemoryPool.MemoryPoolBuilder builder() {
        return new MemoryPool.MemoryPoolBuilder();
    }

    public String getName() {
        return this.name;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public String getType() {
        return this.type;
    }

    public Long getUsed() {
        return this.used;
    }

    public Long getCommited() {
        return this.commited;
    }

    public Long getMax() {
        return this.max;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUsed(Long used) {
        this.used = used;
    }

    public void setCommited(Long commited) {
        this.commited = commited;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof MemoryPool)) {
            return false;
        } else {
            MemoryPool other = (MemoryPool)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                Object this$simpleName = this.getSimpleName();
                Object other$simpleName = other.getSimpleName();
                if (this$simpleName == null) {
                    if (other$simpleName != null) {
                        return false;
                    }
                } else if (!this$simpleName.equals(other$simpleName)) {
                    return false;
                }

                Object this$type = this.getType();
                Object other$type = other.getType();
                if (this$type == null) {
                    if (other$type != null) {
                        return false;
                    }
                } else if (!this$type.equals(other$type)) {
                    return false;
                }

                label62: {
                    Object this$used = this.getUsed();
                    Object other$used = other.getUsed();
                    if (this$used == null) {
                        if (other$used == null) {
                            break label62;
                        }
                    } else if (this$used.equals(other$used)) {
                        break label62;
                    }

                    return false;
                }

                label55: {
                    Object this$commited = this.getCommited();
                    Object other$commited = other.getCommited();
                    if (this$commited == null) {
                        if (other$commited == null) {
                            break label55;
                        }
                    } else if (this$commited.equals(other$commited)) {
                        break label55;
                    }

                    return false;
                }

                Object this$max = this.getMax();
                Object other$max = other.getMax();
                if (this$max == null) {
                    if (other$max != null) {
                        return false;
                    }
                } else if (!this$max.equals(other$max)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof MemoryPool;
    }


    @Override
    public String toString() {
        return "MemoryPool(name=" + this.getName() + ", simpleName=" + this.getSimpleName() + ", type=" + this.getType() + ", used=" + this.getUsed() + ", commited=" + this.getCommited() + ", max=" + this.getMax() + ")";
    }

    public MemoryPool() {
    }

    public MemoryPool(String name, String simpleName, String type, Long used, Long commited, Long max) {
        this.name = name;
        this.simpleName = simpleName;
        this.type = type;
        this.used = used;
        this.commited = commited;
        this.max = max;
    }

    public static class MemoryPoolBuilder {
        private String name;
        private String simpleName;
        private String type;
        private Long used;
        private Long commited;
        private Long max;

        MemoryPoolBuilder() {
        }

        public MemoryPool.MemoryPoolBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MemoryPool.MemoryPoolBuilder simpleName(String simpleName) {
            this.simpleName = simpleName;
            return this;
        }

        public MemoryPool.MemoryPoolBuilder type(String type) {
            this.type = type;
            return this;
        }

        public MemoryPool.MemoryPoolBuilder used(Long used) {
            this.used = used;
            return this;
        }

        public MemoryPool.MemoryPoolBuilder commited(Long commited) {
            this.commited = commited;
            return this;
        }

        public MemoryPool.MemoryPoolBuilder max(Long max) {
            this.max = max;
            return this;
        }

        public MemoryPool build() {
            return new MemoryPool(this.name, this.simpleName, this.type, this.used, this.commited, this.max);
        }
        @Override
        public String toString() {
            return "MemoryPool.MemoryPoolBuilder(name=" + this.name + ", simpleName=" + this.simpleName + ", type=" + this.type + ", used=" + this.used + ", commited=" + this.commited + ", max=" + this.max + ")";
        }
    }
}
