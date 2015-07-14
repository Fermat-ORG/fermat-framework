package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common;

public class ContactsHolder implements ContactsListView{

        private int type;
        private Object object;

    ContactsHolder(int type,Object object){
            this.type=type;
            this.object=object;
        }

        @Override
        public int getType() {
            return type;
        }

        @Override
        public Object getObject() {
            return object;
        }
    }