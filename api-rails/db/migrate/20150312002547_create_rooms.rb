class CreateRooms < ActiveRecord::Migration
  def change
    create_table :rooms, id: false do |t|
     t.integer :user_id
      t.string :room_id, null: false
      t.string :name
    
      t.references :user, index: true

      t.timestamps

    end 
    add_index :rooms, :room_id, unique: true
     add_foreign_key :rooms, :users
  end
end
