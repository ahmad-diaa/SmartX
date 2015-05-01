class AddSecurityAToUsers < ActiveRecord::Migration
  def change
    add_column :users, :securityA, :string
  end
end
