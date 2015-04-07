class CreateNotes < ActiveRecord::Migration
  def change
    create_table :notes do |t|
      t.text :body
      t.references :device, index: true
      t.references :room, index: true
      t.references :user, index: true

      t.timestamps
    end
	add_foreign_key :notes, :devices
    add_foreign_key :notes, :rooms
    add_foreign_key :notes, :users
  end
end
