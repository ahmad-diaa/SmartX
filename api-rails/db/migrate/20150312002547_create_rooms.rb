class CreateRooms < ActiveRecord::Migration
  def change
    create_table :rooms do |t|
      t.integer :user_id
      t.string :name, :null => false
      t.references :user, index: true
      t.timestamps
    end 
     add_foreign_key :rooms, :users
  end
end
