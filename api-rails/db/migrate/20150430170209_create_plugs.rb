class CreatePlugs < ActiveRecord::Migration
  def change
    create_table :plugs do |t|
      t.string :name
      t.string :plug_id
      t.string :user_id
      t.string :room_id
      t.string :status
      t.string :photo

      t.timestamps
    end
  end
end
